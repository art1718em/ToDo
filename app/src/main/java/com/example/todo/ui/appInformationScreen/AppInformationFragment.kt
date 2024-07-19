package com.example.todo.ui.appInformationScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.MainActivity
import com.example.todo.databinding.FragmentAppInformationBinding
import com.example.todo.di.appInformationScreen.AppInformationFragmentComponent
import com.example.todo.navigation.FragmentNavigation
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.picasso.PicassoDivImageLoader
import javax.inject.Inject

class AppInformationFragment : Fragment() {

    private lateinit var component: AppInformationFragmentComponent

    private lateinit var binding: FragmentAppInformationBinding

    @Inject
    lateinit var navigation: FragmentNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = (activity as MainActivity)
            .mainActivityComponent
            .appInformationFragmentComponent()
        component.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppInformationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val divJson = (activity as MainActivity).assetReader.read("DivKitPage.json")
        val cardJson = divJson.getJSONObject("card")

        val divContext = Div2Context(
            baseContext = requireActivity(),
            configuration = createDivConfiguration(),
            lifecycleOwner = this
        )

        val divView = Div2ViewFactory(divContext).createView(cardJson)
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        divView.layoutParams = layoutParams
        binding.root.addView(divView)
    }

    private fun createDivConfiguration() : DivConfiguration {
        val imageLoader : PicassoDivImageLoader = PicassoDivImageLoader(requireContext())
        return DivConfiguration.Builder(imageLoader)
            .visualErrorsEnabled(true)
            .actionHandler(NavigateToNativeDivActionHandler(navigation))
            .build()
    }

}