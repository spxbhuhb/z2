/*
 * Copyright © 2020-2024, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

import hu.simplexion.adaptive.foundation.Adaptive
import hu.simplexion.adaptive.foundation.producer.poll
import hu.simplexion.adaptive.ui.common.fragment.*
import hu.simplexion.adaptive.ui.common.instruction.*
import kotlinx.datetime.LocalDateTime
import sandbox.*
import kotlin.time.Duration.Companion.seconds

@Adaptive
fun login() {

    var counter = 0
    val time = poll(1.seconds) { now() } ?: now()
    val timeText = "${time.hour.twoDigits}:${time.minute.twoDigits}:${time.second.twoDigits}"

    box {

        image(Res.drawable.background)

        grid(
            RowTemplate(140.dp, 50.dp, 1.fr, 1.fr, 1.fr, 50.dp, 100.dp),
            ColTemplate(1.fr)
        ) {
            logo()
            title()
            time(timeText)
            progress(time)
            messages(time, counter)

            grid(
                RowTemplate(50.dp),
                ColTemplate(32.dp, 1.fr, 32.dp, 1.fr, 32.dp)
            ) {

                row(2.gridCol, greenGradient, borderRadius, *center, onClick { counter ++ }) {
                    text("Snooze", white, textMedium, noSelect)
                }

                row(4.gridCol, whiteBorder, borderRadius, *center) {
                    text("Sleepiness: $counter", white, textMedium)
                }
            }

            terms()
        }
    }
}

@Adaptive
private fun logo() {
    row(AlignItems.End, JustifyContent.Center, Padding(bottom = 20.dp)) {
        image(Res.drawable.logo, Size(92.dp, 92.dp))
    }
}

@Adaptive
private fun title() {
    row(AlignItems.Start, JustifyContent.Center) {
        text("Good Morning", white, FontSize(40.sp), LetterSpacing(- 0.02f))
    }
}

@Adaptive
private fun time(timeText: String) {
    column(AlignItems.Center, JustifyContent.Start, Padding(top = 12.dp)) {
        text(timeText, white, FontSize(80.sp), LetterSpacing(- 0.02f))
    }
}

@Adaptive
private fun progress(time: LocalDateTime) {
    row(*center) {
        for (i in 0 .. time.second) {
            text(if (i % 10 == 0) "|" else ".", white)
        }
    }
}

@Adaptive
private fun messages(time: LocalDateTime, counter: Int) {
    column(AlignItems.Center, JustifyContent.Center) {
        if (time.second % 2 == 1) {
            row(AlignItems.Start, JustifyContent.Center, greenGradient, borderRadius, Padding(8.dp)) {
                text("What an odd second!", white)
            }
        }

        if (counter > 3) {
            row(greenGradient, borderRadius, Padding(8.dp)) {
                text("You are really sleepy today!", white, textMedium)
            }
        }
    }
}

@Adaptive
private fun terms() {
    column(AlignItems.Center, Padding(right = 32.dp, left = 32.dp, top = 12.dp)) {
        row {
            text("By joining you agree to our", *smallWhiteNoWrap, Padding(right = 6.dp))
            text("Terms of Service", externalLink(Res.file.terms), *smallWhiteNoWrap, bold, Padding(right = 6.dp))
            text("and", *smallWhiteNoWrap)
        }
        text("Privacy Policy", externalLink(Res.file.policy), *smallWhiteNoWrap, bold)
    }
}