package com.hieupham.data.source.local.dao

import org.junit.Test

class AssetDaoTest : DaoTest() {

    val dao = database.assetDao()

    @Test
    fun testSaveAssets_NoError_DataIsSaved() {
    }

    @Test
    fun testSaveAssets_SQLError_ErrorIsThrow() {
    }

    @Test
    fun testSaveAssets_Duplicate_ReplacedNewOne() {
    }

    @Test
    fun testGetAssets_NoError_DataIsReturn() {
    }

    @Test
    fun testDeleteAssets_NoError_DataIsDeleted() {
    }
}