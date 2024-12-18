package com.example.aniverse.screens.episodes

import YoutubeScreen
import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aniverse.model.AnimeAllEpisodeModel
import com.example.aniverse.ui.theme.BackGroundColor
import com.example.aniverse.viewmodel.AllEpisodeViewModel
import com.example.aniverse.viewmodel.AnimeViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AllEpisodesScreen(navController: NavController,viewModel: AllEpisodeViewModel = hiltViewModel(),url:String,id:Int,index:Int,topanimeviewmodel:AnimeViewModel) {
    val allanimeEpisode by viewModel.getAllEpisode.collectAsState()
    val topAnime by topanimeviewmodel.topAnime.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    LaunchedEffect(Unit) {
       viewModel.fetchAllEpisode(id)
    }
    Scaffold(containerColor = BackGroundColor) { contentPadding ->
        LazyColumn(modifier = Modifier.background(BackGroundColor)) {
            // Header Section
            item {
                Box(
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    Column {
                       YoutubeScreen(videoId =url,Modifier.fillMaxSize())
//                        AsyncImage(
//                            model = ImageRequest.Builder(LocalContext.current)
//                                .data("https://img1.ak.crunchyroll.com/i/spire1-tmb/fccf5fe3454a077e0d68299b7453e5121711114121_large.jpg")
//                                .crossfade(true)
//                                .build(),
//                            contentDescription = "Header Image",
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(240.dp)
//                        )
                        ElevatedButton(
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            elevation = ButtonDefaults.elevatedButtonElevation(
                                defaultElevation = 8.dp,
                                pressedElevation = 12.dp
                            )
                        ) {
                            Text(
                                text = "+ Wishlist",
                                textAlign = TextAlign.Center,
                                style = TextStyle(fontWeight = FontWeight.W600, fontSize = 16.sp)
                            )
                        }
                    }
                }
            }

            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
            }

            if (errorMessage != null) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: $errorMessage",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            if (allanimeEpisode.isEmpty()&& !isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No episodes available.",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                   // contentAlignment = Alignment.Start
                ) {
                   Column {
                       Text(
                           text = "Episode- ${allanimeEpisode.size}",
                           color = Color.White,
                           style = MaterialTheme.typography.bodyMedium,
                           textAlign = TextAlign.Left,
                           fontSize = 22.sp
                       )
                       Spacer(modifier = Modifier.height(10.dp))
                       Text(
                           text = topanimeviewmodel.topAnime.value.get(index).synopsis,
                           color = Color.White,
                           style = TextStyle(fontWeight = FontWeight.W500),
                           textAlign = TextAlign.Left,
                           fontSize = 15.sp
                       )

                   }
                }
            }
//            items(allanimeEpisode.reversed() .size) { index ->
//                val singledata = allanimeEpisode[index]
//                val encodedUrl = Uri.encode(singledata.url)
//
////                EpisodeBox(singledata,{
////                    navController.navigate("videoPlayerScreen/${encodedUrl}")
////                })
//
//                Text("Episode count ${allanimeEpisode.reversed() .size}")
//            }
        }
    }
}
