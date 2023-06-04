package com.solomonboltin.telegramtv.ui

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.solomonboltin.telegramtv.ui.connection.ConnectionUI
import com.solomonboltin.telegramtv.ui.dash.MovieDashLiveUI
import com.solomonboltin.telegramtv.ui.movie.MyContent
import com.solomonboltin.telegramtv.vms.ClientVM
import com.solomonboltin.telegramtv.vms.FilesVM
import com.solomonboltin.telegramtv.vms.MovieDashVM
import org.drinkless.td.libcore.telegram.TdApi
import org.koin.androidx.compose.koinViewModel

@Preview()
@Composable
fun MainUI() {
    Log.i("MainUI", "Starting main ui")

    val clientVM = koinViewModel<ClientVM>()
    val connectionState by clientVM.connectionState.collectAsState()
    val clientState by clientVM.authState.collectAsState()



    val filesVM = koinViewModel<FilesVM>()
    val playingMovie by filesVM.playingMovie.collectAsState()

    val movieDashVM = koinViewModel<MovieDashVM>()


    val user by clientVM.user.collectAsState()


//    val itemsList = (0..5).toList()
//
//    LazyRow(){
//        items(itemsList){
//            Box(modifier = Modifier.background(Color.Yellow).size(100.dp).border(BorderStroke(3.dp, Color.Black)).padding(start = 10.dp)){
//            }
//        }
//    }

    when(connectionState){
        is TdApi.ConnectionStateConnecting -> {
            Text(text = "Connecting to telegram")
        }
        is TdApi.ConnectionStateReady -> {
            when (clientState) {
                is TdApi.AuthorizationStateReady -> {
                    if (playingMovie == null) {
                        movieDashVM.start()
                        MovieDashLiveUI()
                    } else {
                        println("Paling MyContent ")
                        MyContent(playingMovie!!)
                    }

                }
                else -> {
                    ConnectionUI()
                }
            }
        }
        is TdApi.ConnectionStateConnectingToProxy -> {
            Text(text = "Connecting to proxy")
        }
        is TdApi.ConnectionStateUpdating -> {
            Text(text = "Updating")
        }
        is TdApi.ConnectionStateWaitingForNetwork -> {
            Text(text = "Waiting for network")
        }
        else -> {
            Text(text = "Unknown connection state $connectionState")
        }
    }

//    MaterialTheme{
//        RelayContainer{
//            Text(text = "Hello world" )
//            Column {
//                MovieView(
//                    Modifier
//                        .widthIn(0.dp, 250.dp)
//                        .heightIn(0.dp, 420.dp))
//                Icon(Icons.Outlined.ThumbUp, contentDescription = "null")
//            }
//        }
//    }
//
//    Tv(                Modifier
//        .fillMaxSize()
//        .widthIn(0.dp, 890.dp)
////        .boxAlign(Alignment.Center, DpOffset.Unspecified)
////            .horizontalScroll(ScrollState(0))
////            .verticalScroll(ScrollState(0))
//    )

}


//    MaterialTheme {
//        RelayContainer {
//
//
////            when (clientState) {
////                is TdApi.AuthorizationStateReady -> {
////                    if(playingMovie == null){
////                        MoviesDashUI()
////                    }
////                    else{
//////                        PlayMovieUI(movie = playingMovie!!)
////                        println("Paling MyContent ")
////                        MyContent(playingMovie!!)
////                    }
////
////                }
////                else -> {
////                    ConnectionUI()
////                }
////            }
//
//        }
//    }


