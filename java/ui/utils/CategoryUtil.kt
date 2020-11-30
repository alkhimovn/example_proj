package com.example.job.util

import androidx.databinding.ObservableField
import com.example.job.App
import com.example.job.R
import com.example.job.network.data.input.Category
import com.example.job.ui.adapter.EditItem

object CategoryUtil {

    fun getAllCategory(): List<EditItem>{

        val list = listOf<EditItem>(
            EditItem(
                ObservableField(getTextSearchSettings(Category.ANY)),
                ObservableField(Category.ANY.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.SIDE_JOB)),
                ObservableField(Category.SIDE_JOB.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.PUBLIC_CATERING)),
                ObservableField(Category.PUBLIC_CATERING.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.ENTRY_LEVEL)),
                ObservableField(Category.ENTRY_LEVEL.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.COMMERCE)),
                ObservableField(Category.COMMERCE.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.ADMINISTRATIVE_STAFF)),
                ObservableField(Category.ADMINISTRATIVE_STAFF.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.CONSTRUCTION)),
                ObservableField(Category.CONSTRUCTION.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.LOGISTICS)),
                ObservableField(Category.LOGISTICS.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.SERVICE_SECTOR)),
                ObservableField(Category.SERVICE_SECTOR.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.ENTERTAINMENT)),
                ObservableField(Category.ENTERTAINMENT.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.AUTO)),
                ObservableField(Category.AUTO.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.SALES)),
                ObservableField(Category.SALES.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.MARKETING)),
                ObservableField(Category.MARKETING.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.IT)),
                ObservableField(Category.IT.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.SECURITY)),
                ObservableField(Category.SECURITY.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.FINANCE)),
                ObservableField(Category.FINANCE.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.CLEANING)),
                ObservableField(Category.CLEANING.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.HEALTH)),
                ObservableField(Category.HEALTH.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.BEAUTY)),
                ObservableField(Category.BEAUTY.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.INDUSTRY)),
                ObservableField(Category.INDUSTRY.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.SCIENCE_AND_EDUCATION)),
                ObservableField(Category.SCIENCE_AND_EDUCATION.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.AIR_AND_SEA)),
                ObservableField(Category.AIR_AND_SEA.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.ART_AND_CREATIVITY)),
                ObservableField(Category.ART_AND_CREATIVITY.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.OTHER)),
                ObservableField(Category.OTHER.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.HOUSEHOLD_STAFF)),
                ObservableField(Category.HOUSEHOLD_STAFF.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.FARM)),
                ObservableField(Category.FARM.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.ENGINEERING)),
                ObservableField(Category.ENGINEERING.type)
            ),
            EditItem(
                ObservableField(getTextSearchSettings(Category.CIVIL_SERVICE)),
                ObservableField(Category.CIVIL_SERVICE.type)
            )
        )

        return list
    }

    @JvmStatic
    fun getTextSearchSettings(type: Category?): String {

        var strResult: String = App.resourses!!.getString(
            R.string.format_string_category_any
        )

        if (type != null)
            when (type) {
                Category.ANY -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_any
                )
                Category.ADMINISTRATIVE_STAFF -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_administrative_staff
                )
                Category.AIR_AND_SEA -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_air_and_sea
                )
                Category.ART_AND_CREATIVITY -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_art_and_creativity
                )
                Category.AUTO -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_auto
                )
                Category.BEAUTY -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_beauty
                )
                Category.CIVIL_SERVICE -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_civil_service
                )
                Category.CLEANING -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_cleaning
                )
                Category.COMMERCE -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_commerce
                )
                Category.CONSTRUCTION -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_construction
                )
                Category.ENGINEERING -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_engineering
                )
                Category.ENTERTAINMENT -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_entertainment
                )
                Category.ENTRY_LEVEL -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_entry_level
                )
                Category.FARM -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_farm
                )
                Category.FINANCE -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_finance
                )
                Category.HEALTH -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_health
                )
                Category.HOUSEHOLD_STAFF -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_household_staff
                )
                Category.INDUSTRY -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_industry
                )
                Category.IT -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_it
                )
                Category.LOGISTICS -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_logistics
                )
                Category.MARKETING -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_marketing
                )
                Category.OTHER -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_other
                )
                Category.PUBLIC_CATERING -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_public_catering
                )
                Category.SALES -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_sales
                )
                Category.SCIENCE_AND_EDUCATION -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_science_and_education
                )
                Category.SECURITY -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_security
                )
                Category.SERVICE_SECTOR -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_service_sector
                )
                Category.SIDE_JOB -> strResult = App.resourses!!.getString(
                    R.string.format_string_category_side_job
                )
                else -> {
                    strResult = App.resourses!!.getString(
                        R.string.format_string_category_any
                    )
                }
            }
        else
            strResult = App.resourses!!.getString(
                R.string.format_string_category_any
            )

        return strResult
    }

}
