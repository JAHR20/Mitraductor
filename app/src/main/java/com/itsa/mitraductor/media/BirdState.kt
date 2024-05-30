package com.itsa.mitraductor.media

import android.provider.SyncStateContract
import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import com.google.common.base.Converter
import com.itsa.mitraductor.media.Constants.Companion.birdHeight
import com.itsa.mitraductor.media.Converter.convertDpToPixels

//This state defines various characterstic of bird like After which score it will get draw on the
// ui and position of the bird.

class BirdState(var xpos :Dp = Constants.roadLength, var crossDino :Boolean = false, var targetScore : Int = 3) {
    var isMoving = false

    //changes the position of bird by reducing xpos value.
    fun move(score : MutableState<Int>){
        xpos -= Constants.xVelocity
        if (!crossDino && xpos < Constants.dinoPos) {
            score.value++
            crossDino = true
        }
    }

    fun destroy(){
        xpos = Constants.roadLength
    }

    //set target score at which bird will appear in the game.
    fun increaseTargetedScore(){
        targetScore = (targetScore+3..targetScore+6).random()
    }

    fun draw(drawScope: DrawScope){
        val birdXpos = convertDpToPixels(xpos.value)
        drawScope.apply {
            withTransform({
                translate(
                    left = birdXpos,
                    top = convertDpToPixels(birdHeight.value) - AssetPath().BirdPath().getBounds().height
                )
            }) {
                drawPath(
                    path = AssetPath().BirdPath(),
                    color = Color.Red
                )
            }
        }
    }


    fun getRect() : Rect {
        val resource = AssetPath().BirdPath() //Function to get rectangle around the bird.
        return  Rect(
            left = convertDpToPixels(xpos.value),
            top =  convertDpToPixels(birdHeight.value) - resource.getBounds().height,
            right = convertDpToPixels(xpos.value) + resource.getBounds().width,
            bottom = convertDpToPixels(birdHeight.value)
        )
    }
}