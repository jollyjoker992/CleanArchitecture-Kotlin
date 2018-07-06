package com.hieupham.data.source.local.dao

import org.junit.Test

class BlockDaoTest : DaoTest() {

    val dao = database.blockDao()

    @Test
    fun testSaveBlocks_NoError_DataIsSaved() {
    }

    @Test
    fun testSaveBlocks_SQLError_ErrorIsThrow() {
    }

    @Test
    fun testSaveBlocks_Duplicate_ReplacedNewOne() {
    }

    @Test
    fun testGetBlocks_NoError_DataIsReturn() {
    }

    @Test
    fun testDeleteBlocks_NoError_DataIsDeleted() {
    }

}