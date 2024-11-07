package com.example.myapp

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import java.util.Locale

object LocaleHelper {

    fun setLocale(context: Context, languageCode: String): ContextWrapper {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        // Создаем новый контекст с измененной конфигурацией
        val newContext = context.createConfigurationContext(config)
        return ContextWrapper(newContext)
    }

}
