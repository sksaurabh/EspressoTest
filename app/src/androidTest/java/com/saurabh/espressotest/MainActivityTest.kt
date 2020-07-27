/*
 * Copyright (c) 2019 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.emojicalculator

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.raywenderlich.android.emojicalculator.ScreenRobot.Companion.withRobot
import com.saurabh.espressotest.MainActivity
import com.saurabh.espressotest.R
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
  @Test
  fun appLaunchesSuccessfully() {
    ActivityScenario.launch(MainActivity::class.java)
  }

  @Test
  fun onLaunchCheckAmountInputIsDisplayed() {
    ActivityScenario.launch(MainActivity::class.java)

    onView(withId(R.id.inputAmount))
        .check(matches(isDisplayed()))
  }

  @Test
  fun onLaunchOkayButtonIsDisplayed() {
    ActivityScenario.launch(MainActivity::class.java)

    onView(withText(R.string.okay))
        .check(matches(isDisplayed()))
  }

  @Test
  fun whenOkayButtonIsPressedAndAmountIsEmptyTipIsEmpty() {
    ActivityScenario.launch(MainActivity::class.java)

    onView(withId(R.id.buttonOkay))
        .perform(click())

    onView(allOf(withId(R.id.textTip), withText("")))
        .check(matches(isDisplayed()))
  }

  @Test
  fun whenOkayButtonIsPressedAndAmountIsFilledTipIsSet() {
    ActivityScenario.launch(MainActivity::class.java)

    withRobot(CalculatorScreenRobot::class.java)
        .inputCheckAmountAndSelectOkayButton("11")
        .verifyTipIsCorrect("1.98")
  }

  @Test
  fun whenOkayButtonIsPressedAndRoundSwitchIsSelectedAmountIsCorrect() {
    ActivityScenario.launch(MainActivity::class.java)

    withRobot(CalculatorScreenRobot::class.java)
        .clickOkOnView(R.id.switchRound)
        .inputCheckAmountAndSelectOkayButton("11")
        .verifyTipIsCorrect("2.00")
  }

  class CalculatorScreenRobot : ScreenRobot<CalculatorScreenRobot>() {
    fun verifyTipIsCorrect(tip: String): CalculatorScreenRobot {
      return checkViewHasText(R.id.textTip, tip)
    }

    fun inputCheckAmountAndSelectOkayButton(input: String):
        CalculatorScreenRobot {
      return enterTextIntoView(R.id.inputAmount, input)
          .clickOkOnView(R.id.buttonOkay)
    }
  }
}