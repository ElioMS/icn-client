package com.peruapps.icnclient.db.sharePreferences

import android.app.backup.BackupManager
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class PowerPreferences(context: Context): SharedPreferences.OnSharedPreferenceChangeListener{

    private var mPreferences: SharedPreferences
    var mBackupManager: BackupManager = BackupManager(context)
    var mFileName = "preferences"

    init {
        mPreferences = context.getSharedPreferences(mFileName, Context.MODE_PRIVATE)
        mPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        mBackupManager.dataChanged()
    }

    /**
     * Guarda un Objeto de tipo [Any] serializado en string con ayuda de Gson.
     *
     * @param key    : clave para alacenarlo en la preferencia.
     * @param obt : [Any] a ser guardado en la preferencia.
     */
    fun saveObject(key: String, obt: Any) {
        val json =  Gson().toJson(obt)
        mPreferences.edit().putString(key, json).apply()
    }

    /**
     * Obtiene un objeto de tipo [T] desde un objeto serializado por Gson.
     *
     * @param key          : clave para alacenarlo en la preferencia.
     * @param defaultValue : Valor por defecto si no se encuentra en la base de las preferencias.
     * @return [T]
     */
    fun  <T> getObject(key: String, clazz: Class<T> , defaultValue: Any):T {
        var json = mPreferences.getString(key, "")
        if (json == "") {
            saveObject(key, defaultValue)
            json = mPreferences.getString(key, null)
        }
        //Type type = new TypeToken<T>(){}.getType();
        return Gson().fromJson(json, clazz)
    }

    fun saveString(name: String, value: String) {
        mPreferences.edit()
            .putString(name, value)
            .apply()
    }

    fun getString(name: String, defaultValue: String): String {
        return mPreferences.getString(name, defaultValue)!!
    }

    fun saveBoolean(name: String, value: Boolean) {
        mPreferences.edit()
            .putBoolean(name, value)
            .apply()
    }
    fun getBoolean(name: String, defaultValue: Boolean): Boolean {
        return mPreferences.getBoolean(name, defaultValue)
    }
    /**
     * Borra un item especifico del preferencia en base a su clave.
     *
     * @param key : Clave de la preferencia.
     */
    fun removeItemPreference(key: String) {
        mPreferences.edit().remove(key).apply()
    }

    /**
     * Borra toda la preferencia.
     */
    fun clearAllPreferences() {
        mPreferences.edit().clear().apply()
    }
}