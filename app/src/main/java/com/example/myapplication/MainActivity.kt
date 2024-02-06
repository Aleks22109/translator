package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var items= arrayOf("English","Hindi","Bengali","Gujarati","Tamil","Telugu")

    private var conditions= DownloadConditions.Builder().requireWifi().build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        val itemAdapter:ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, items)
        binding.languageFrom.setAdapter(itemAdapter)
        binding.languageTo.setAdapter(itemAdapter)
        binding.translate.setOnClickListener {
            val options=TranslatorOptions.Builder()
                .setSourceLanguage(selectFrom())
                .setTargetLanguage(selectTo())
                .build()
            val translator=Translation.getClient(options)
            translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener {
                    translator.translate(binding.input.text.toString())
                        .addOnSuccessListener {
                            binding.output.text=it

                        }
                        .addOnFailureListener {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener {
                    Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                }

        }
    }

    private fun selectFrom(): String {
        return when(binding.languageFrom.text.toString()){
            ""->TranslateLanguage.ENGLISH
            "English"->TranslateLanguage.ENGLISH
            "Hindi"->TranslateLanguage.HINDI
            "Bengali"->TranslateLanguage.BENGALI
            "Gujarati"->TranslateLanguage.GUJARATI
            "Tamil"->TranslateLanguage.TAMIL
            "Telugu"->TranslateLanguage.TELUGU
            else->TranslateLanguage.ENGLISH
        }

    }

    private fun selectTo(): String {
        return when(binding.languageTo.text.toString()){
            ""->TranslateLanguage.HINDI
            "English"->TranslateLanguage.ENGLISH
            "Hindi"->TranslateLanguage.HINDI
            "Bengali"->TranslateLanguage.BENGALI
            "Gujarati"->TranslateLanguage.GUJARATI
            "Tamil"->TranslateLanguage.TAMIL
            "Telugu"->TranslateLanguage.TELUGU
            else->TranslateLanguage.HINDI }

    }
}
