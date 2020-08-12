package com.jaozinfs.paging.database.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jaozinfs.paging.database.local.entities.GenreEntity
import com.jaozinfs.paging.database.local.entities.series.NetworkEntity
import com.jaozinfs.paging.database.local.entities.series.SeasonEntity
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromString(value: String): List<Int> {
        val listType: Type = object : TypeToken<ArrayList<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<Int>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromGenres(value: String?): List<GenreEntity> {
        val listType: Type = object : TypeToken<ArrayList<GenreEntity>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun FromArraGenres(list: List<GenreEntity>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromSeasonEntity(value: String?): List<SeasonEntity> {
        val listType: Type = object : TypeToken<ArrayList<SeasonEntity>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArraySeason(list: List<SeasonEntity>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromNetworkEntity(value: String?): List<NetworkEntity> {
        val listType: Type = object : TypeToken<ArrayList<NetworkEntity>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayNetwork(list: List<NetworkEntity>): String {
        val gson = Gson()
        return gson.toJson(list)
    }


    @TypeConverter
    fun fromStrings(value: String?): List<String> {
        val listType: Type = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayString(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}