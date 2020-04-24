package com.me.coloranimation

import android.graphics.Color

class ColorAnimationHelper(private val fromColor: Int, private val toColor: Int, private val mode: Mode = Mode.RGB) {

    private val colorGenerator: ColorGenerator

    init {
        colorGenerator = when (mode) {
            Mode.HSV -> HSVColorGenerator(fromColor, toColor)
            Mode.RGB -> RGBColorGenerator(fromColor, toColor)
        }
    }

    fun getColor(interpolatedTime: Float) = colorGenerator.getColor(interpolatedTime)

    companion object {
        enum class Mode {
            HSV, RGB
        }

        private interface ColorGenerator {
            fun getColor(interpolatedTime: Float): Int
        }

        private class RGBColorGenerator(private val fromColor: Int, private val toColor: Int) : ColorGenerator {
            private val from = Triple(Color.red(fromColor), Color.green(fromColor), Color.blue(fromColor))
            private val to = Triple(Color.red(toColor), Color.green(toColor), Color.blue(toColor))
            private val changeFactor: Triple<Int, Int, Int>

            init {
                changeFactor = Triple(
                    to.first.minus(from.first),
                    to.second.minus(from.second),
                    to.third.minus(from.third)
                )
            }

            override fun getColor(interpolatedTime: Float): Int {
                return rgbToColor(
                    from.first.plus(changeFactor.first * interpolatedTime).toInt(),
                    from.second.plus(changeFactor.second * interpolatedTime).toInt(),
                    from.third.plus(changeFactor.third * interpolatedTime).toInt()
                )
            }

            private fun rgbToColor(r: Int, g: Int, b: Int): Int {
                return -0x1000000 or (r shl 16) or (g shl 8) or b
            }
        }

        private class HSVColorGenerator(private val fromColor: Int, private val toColor: Int) : ColorGenerator {
            private val from = FloatArray(3)
            private val to = FloatArray(3)
            private val hsv = FloatArray(3)

            init {
                Color.colorToHSV(fromColor, from)
                Color.colorToHSV(toColor, to)
            }

            override fun getColor(interpolatedTime: Float): Int {
                hsv[0] = from[0] + (to[0] - from[0]) * interpolatedTime
                hsv[1] = from[1] + (to[1] - from[1]) * interpolatedTime
                hsv[2] = from[2] + (to[2] - from[2]) * interpolatedTime
                return Color.HSVToColor(hsv)
            }
        }

    }

}