package com.msarangal.newsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.msarangal.newsapp.data.local.entities.asExternalModel
import com.msarangal.newsapp.data.model.Article
import com.msarangal.newsapp.databinding.FragmentMainBinding
import com.msarangal.newsapp.ui.practice.ArticlesAdapter
import com.msarangal.newsapp.ui.practice.PracticeViewModel
import com.msarangal.newsapp.util.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var tokenManager: TokenManager

    private val articlesAdapter: ArticlesAdapter = ArticlesAdapter(getFakeArticles())

    private val viewModel: PracticeViewModel by activityViewModels<PracticeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireActivity(), tokenManager.getToken(), Toast.LENGTH_SHORT).show()
        bindObserver()
        val manager = LinearLayoutManager(requireActivity())
        manager.orientation = LinearLayoutManager.VERTICAL
//        binding.rvArticles.adapter = articlesAdapter
//        binding.rvArticles.layoutManager = manager
        binding.rvArticles.apply {
            layoutManager = manager
            adapter = articlesAdapter
        }

    }

    private fun bindObserver() {
        viewModel.healthsNewsLiveData.observe(viewLifecycleOwner, Observer { articleEntitiesList ->
            articlesAdapter.list =
                articleEntitiesList.map { articleEntity -> articleEntity.asExternalModel() }
            articlesAdapter.notifyDataSetChanged()
        })
    }

    private fun getFakeArticles(): List<Article> = listOf(
        Article(
            title = "Title one",
            author = "Author one",
            imgUrl = "url 1",
            dateTime = "",
            category = "Category"
        ),
        Article(
            title = "Title two",
            author = "Author one",
            imgUrl = "url 1",
            dateTime = "",
            category = "Category"
        ),
        Article(
            title = "Title three",
            author = "Author one",
            imgUrl = "url 1",
            dateTime = "",
            category = "Category"
        ),
        Article(
            title = "Title four",
            author = "Author one",
            imgUrl = "url 1",
            dateTime = "",
            category = "Category"
        )
    )
}