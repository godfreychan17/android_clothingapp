package com.godfrey.endcodingtestproject

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.godfrey.endclothingtestproject.productlistpage.view.ProductListActivity

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import org.hamcrest.core.AllOf.allOf

import com.godfrey.endclothingtestproject.R
import com.godfrey.endclothingtestproject.resource.Resources
import org.junit.Assert

@RunWith(AndroidJUnit4::class)
class ProductListActivityTest {
    @Rule
    @JvmField
    val activityTest = ActivityTestRule(ProductListActivity::class.java)

    @Test
    fun testViewSettingSpinner(){
        onView( withId(R.id.ProductList_ViewSpinner))
            .perform( click())
        onView(allOf (withId(R.id.SpinnerItem_Title), withText("Large"))).perform(click());
        var selectedItems = activityTest.activity.getViewSpinnerArrayAdapter().getSelectedItems()
        var exceptedValue = Resources.ViewSetting.Product.bitValue or Resources.ViewSetting.Large.bitValue
        Assert.assertEquals(exceptedValue, selectedItems)
        Assert.assertEquals(1, activityTest.activity.getGridLayoutManager().spanCount)

        onView( withId(R.id.ProductList_ViewSpinner))
            .perform( click())
        onView(allOf (withId(R.id.SpinnerItem_Title), withText("Small"))).perform(click());
        selectedItems = activityTest.activity.getViewSpinnerArrayAdapter().getSelectedItems()
        exceptedValue = Resources.ViewSetting.Product.bitValue or Resources.ViewSetting.Small.bitValue
        Assert.assertEquals(exceptedValue, selectedItems)
        Assert.assertEquals(2, activityTest.activity.getGridLayoutManager().spanCount)

        onView( withId(R.id.ProductList_ViewSpinner))
            .perform( click())
        onView(allOf (withId(R.id.SpinnerItem_Title), withText("Outfit"))).perform(click());
        selectedItems = activityTest.activity.getViewSpinnerArrayAdapter().getSelectedItems()
        exceptedValue = Resources.ViewSetting.Outfit.bitValue or Resources.ViewSetting.Small.bitValue
        Assert.assertEquals(exceptedValue, selectedItems)

        onView( withId(R.id.ProductList_ViewSpinner))
            .perform( click())
        onView(allOf (withId(R.id.SpinnerItem_Title), withText("Product"))).perform(click());
        selectedItems = activityTest.activity.getViewSpinnerArrayAdapter().getSelectedItems()
        exceptedValue = Resources.ViewSetting.Product.bitValue or Resources.ViewSetting.Small.bitValue
        Assert.assertEquals(exceptedValue, selectedItems)
    }

    @Test
    fun testSortingSpinner(){
        onView( withId(R.id.ProductList_SortSpinner))
            .perform( click())
        onView(allOf (withId(R.id.SpinnerItem_Title), withText("Latest"))).perform(click());
        var selectedItems = activityTest.activity.getSortSpinnerArrayAdapter().getSelectedItems()
        Assert.assertEquals(Resources.Sorting.Latest.bitValue, selectedItems)

        onView( withId(R.id.ProductList_SortSpinner))
            .perform( click())
        onView(allOf (withId(R.id.SpinnerItem_Title), withText("Price (High)"))).perform(click());
        selectedItems = activityTest.activity.getSortSpinnerArrayAdapter().getSelectedItems()
        Assert.assertEquals(Resources.Sorting.PriceHigh.bitValue, selectedItems)

        onView( withId(R.id.ProductList_SortSpinner))
            .perform( click())
        onView(allOf (withId(R.id.SpinnerItem_Title), withText("Price (Low)"))).perform(click());
        selectedItems = activityTest.activity.getSortSpinnerArrayAdapter().getSelectedItems()
        Assert.assertEquals(Resources.Sorting.PriceLow.bitValue, selectedItems)

    }
}