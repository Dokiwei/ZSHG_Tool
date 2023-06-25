package com.dokiwei.zshg.tool.ui.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry


fun enterScreenAnim(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? {
    return {
        fadeIn(tween(200)
        )
    }
}
fun enterScreenRightAnim(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? {
    return {
        slideInHorizontally(tween(200)) { fullWidth ->
            +fullWidth / 3
        } + fadeIn(tween(200)
        )
    }
}
fun enterScreenLeftAnim(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? {
    return {
        slideInHorizontally(tween(200)) { fullWidth ->
            -fullWidth / 3
        } + fadeIn(tween(200)
        )
    }
}
fun enterSubScreenAnim(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? {
    return {
        scaleIn(tween(200)
        )
    }
}

fun exitScreenAnim() : AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? {
    return {
        fadeOut()
    }
}
fun exitScreenRightAnim() : AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? {
    return {
        slideOutHorizontally(spring(Spring.StiffnessHigh)) {
            200
        } + fadeOut()
    }
}
fun exitScreenLeftAnim() : AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? {
    return {
        slideOutHorizontally(spring(Spring.StiffnessHigh)) {
            -200
        } + fadeOut()
    }
}
fun exitSubScreenAnim() : AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? {
    return {
        scaleOut()
    }
}