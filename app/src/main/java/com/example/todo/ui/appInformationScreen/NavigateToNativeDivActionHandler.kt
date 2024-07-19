package com.example.todo.ui.appInformationScreen

import android.content.Context
import android.net.Uri
import com.example.todo.navigation.FragmentNavigation
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction

class NavigateToNativeDivActionHandler(
    private val navigation: FragmentNavigation,
) : DivActionHandler() {

    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver
    ): Boolean {

        val url = action.url?.evaluate(resolver) ?: return super.handleAction(action, view, resolver)

        return if (url.scheme == ACTION && handleNavDivAction(url, view.view.context)) {
            true
        } else {
            super.handleAction(action, view, resolver)
        }

    }


    private fun handleNavDivAction(action: Uri, context: Context) : Boolean {
        return when(action.host){
            "navigate_to_native" -> {
                navigation.navigateBack()
                true
            }
            else -> false
        }
    }

    companion object {
        const val ACTION = "navigate-action"
    }

}