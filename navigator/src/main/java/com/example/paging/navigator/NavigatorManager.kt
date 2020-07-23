package com.example.paging.navigator

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

/**
 * Starta um novo fluxo e sem remover os "Stacks Anteriores"
 */
infix fun Context.navigateFeature(navigation: Navigations) {
    splitStall(this, navigation) {
        val i = Intent().apply {
            setClassName(this@navigateFeature, navigation.className)
        }
        startActivity(i)
    }
}

/**
 * Starta um novo fluxo e remove os "Stacks Anteriores"
 */
infix fun Context.navigateFeatureClearFlags(navigation: Navigations) {
    splitStall(this, navigation) {
        val i = Intent().apply {
            setClassName(this@navigateFeatureClearFlags, navigation.className)
        }
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(i)
    }
}

/**
 * Verifica se o modulo está instalado,
 * tenta instalar e abri-lo em seguida.
 */
private fun splitStall(
    context: Context,
    navigation: Navigations,
    doOnSuccess: SplitInstallManager.() -> Unit
) {
    val splitInstallManager = SplitInstallManagerFactory.create(context)
    val request = SplitInstallRequest.newBuilder()
        .addModule(navigation.moduleName)
        .build()
    splitInstallManager.startInstall(request)
    if (splitInstallManager.installedModules.contains(navigation.moduleName)) {
        doOnSuccess.invoke(splitInstallManager)
    } else {
        Log.e("Teste", "Registration feature is not installed")
    }
}