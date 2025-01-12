package de.dertyp7214.rboardthememanager

import de.dertyp7214.rboardthememanager.core.getSystemProperty
import de.dertyp7214.rboardthememanager.data.ModuleMeta

@Suppress("MemberVisibilityCanBePrivate", "unused", "SdCardPath")
object Config {
    var useMagisk = false
    var newGboard = true
    var THEME_LOCATION = "/system/etc/gboard_theme"

    val PLAY_URL = { packageName: String ->
        "https://play.google.com/store/apps/details?id=$packageName"
    }

    const val MODULES_PATH = "/data/adb/modules"

    const val MAGISK_PACKAGE_NAME = "com.topjohnwu.magisk"
    const val MODULE_ID = "rboard-themes"
    const val MODULE_PATH = "$MODULES_PATH/$MODULE_ID"
    const val GBOARD_PACKAGE_NAME = "com.google.android.inputmethod.latin"
    const val RBOARD_THEME_CREATOR_PACKAGE_NAME = "de.dertyp7214.rboardthemecreator"

    val MODULE_META = ModuleMeta(
        MODULE_ID,
        "Rboard Themes",
        "v30",
        "300",
        "RKBDI & DerTyp7214",
        "Module for Rboard Themes app"
    )

    val IS_MIUI = "ro.miui.ui.version.name".getSystemProperty().isNotEmpty()

    val GBOARD_PREFS_PATH: String
        get() {
            return newGboard.let {
                if (it) "/data/user_de/0/$GBOARD_PACKAGE_NAME/shared_prefs/${GBOARD_PACKAGE_NAME}_preferences.xml"
                else "/data/data/$GBOARD_PACKAGE_NAME/shared_prefs/${GBOARD_PACKAGE_NAME}_preferences.xml"
            }
        }

    val MAGISK_THEME_LOC: String
        get() {
            return if (!useMagisk) "/data/data/$GBOARD_PACKAGE_NAME/files/themes"
            else if (!THEME_LOCATION.startsWith("/system")) THEME_LOCATION else "$MODULE_PATH$THEME_LOCATION"
        }

    val PACKS_URLS =
        listOf(
            "true:https://raw.githubusercontent.com/GboardThemes/PackRepoBeta/main/list.json",
            "true:https://raw.githubusercontent.com/AkosPaha/PackRepoBeta/main/list.json"
        )
    val REPOS = ArrayList(PACKS_URLS)

    const val SOUNDS_PACKS_URL =
        "https://raw.githubusercontent.com/GboardThemes/Soundpack/master/download_sounds.json"

    var themeCount: Int? = null

    var darkTheme: String? = null
    var lightTheme: String? = null
}