package org.idnp.datastoresamplegra

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.idnp.datastoresamplegra.models.Signature

class MainActivity2 : AppCompatActivity() {
    private val Context.dataStore by preferencesDataStore(NotePrefs.PREFS_NAME)
    lateinit var notePrefs: NotePrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_personalized)

        notePrefs = NotePrefs(dataStore)

        var period_a: EditText = findViewById(R.id.period_a)
        var school: EditText = findViewById(R.id.school)
        var code: EditText = findViewById(R.id.code)
        var name: EditText = findViewById(R.id.name)
        var period_s: EditText = findViewById(R.id.period_s)
        var last: EditText = findViewById(R.id.last)

        var btnSave: Button = findViewById(R.id.btn_save)

        btnSave.setOnClickListener{
            lifecycleScope.launch{
                var signature = Signature(
                    period_a.text.toString(),
                    school.text.toString(),
                    code.text.toString(),
                    name.text.toString(),
                    period_s.text.toString(),
                    last.text.toString()
                )
                notePrefs.saveSignature(signature)
                period_a.setText("")
                school.setText("")
                code.setText("")
                name.setText("")
                period_s.setText("")
                last.setText("")
            }
        }

        var btnLoad: Button = findViewById(R.id.btn_load)
        btnLoad.setOnClickListener {
            lifecycleScope.launch {
                notePrefs.getSignature.collect { signature ->
                    period_a.setText(signature.period_a)
                    school.setText(signature.school)
                    code.setText(signature.code)
                    name.setText(signature.name)
                    period_s.setText(signature.period_s)
                    last.setText(signature.last)
                }
            }
        }
        lateinit var layoutConst: LinearLayoutCompat
        layoutConst = findViewById(R.id.form_layout)

        var btnSettings: Button = findViewById(R.id.btn_settings)
        btnSettings.setOnClickListener{
            lifecycleScope.launch{
                notePrefs.getSettings.collect { settings ->
                    period_a.setTextSize(settings.size_label)
                    period_s.setTextSize(settings.size_label)
                    school.setTextSize(settings.size_label)
                    code.setTextSize(settings.size_label)
                    name.setTextSize(settings.size_label)
                    last.setTextSize(settings.size_label)
                    layoutConst.setBackgroundColor(Integer.parseInt(settings.color.toString()))
                }
            }
        }

    }
}