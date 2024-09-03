package com.example.shoppingapp

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.PratikFagadiya.smoothanimationbottombar.model.SmoothAnimationBottomBarScreens
import com.PratikFagadiya.smoothanimationbottombar.properties.BottomBarProperties
import com.PratikFagadiya.smoothanimationbottombar.ui.SmoothAnimationBottomBar
import com.example.shoppingapp.store.presentation.view_models.CartViewModel
import com.example.shoppingapp.store.presentation.view_models.ProductViewModel
import com.example.shoppingapp.store.presentation.profile_screen.ProfileScreen
import com.example.shoppingapp.store.presentation.product_screen.ProductScreen
import com.example.shoppingapp.util.Routes

@Composable
fun NavigationBar() {
    val navController = rememberNavController()
    val currentIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    val bottomNavigationItems = listOf(
        SmoothAnimationBottomBarScreens(
            Routes.productScreen,
            "Store",
            R.drawable.cart,
        ),
        SmoothAnimationBottomBarScreens(
            Routes.profileScreen,
            "Profile",
            R.drawable.person
        )
    )

    Scaffold(bottomBar = {
        SmoothAnimationBottomBar(
            navController,
            bottomNavigationItems,
            initialIndex = currentIndex,
            bottomBarProperties = BottomBarProperties(),
            onSelectItem = {
                Log.i("SELECTED_ITEM", "onCreate: Selected Item ${it.name}")
            }
        )
    }) { innerPadding ->
        Modifier.padding(innerPadding)
        ScreenNavigationConfiguration(navController, currentIndex)
    }
}

@Composable
fun ScreenNavigationConfiguration(
    navController: NavHostController,
    currentIndex: MutableIntState
) {
    NavHost(navController = navController, startDestination = Routes.productScreen) {
        composable(Routes.productScreen) {
            ProductScreen(
                productViewModel = hiltViewModel<ProductViewModel>(),
                cartViewModel = hiltViewModel<CartViewModel>(),
            )
        }
        composable(Routes.profileScreen) {
            ProfileScreen(
                productViewModel = hiltViewModel<ProductViewModel>(),
                cartViewModel = hiltViewModel<CartViewModel>(),
            )
        }
    }
}

