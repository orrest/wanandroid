package name.zzhxufeng.wanandroid.ui.screens.drawer.items.coin

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.HelpCenter
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import name.zzhxufeng.wanandroid.R
import name.zzhxufeng.wanandroid.data.model.RankModel
import name.zzhxufeng.wanandroid.event.drawer.CoinEvent
import name.zzhxufeng.wanandroid.state.drawer.CoinUiState
import name.zzhxufeng.wanandroid.ui.composables.BorderedItemColumn
import name.zzhxufeng.wanandroid.ui.composables.WanTopBar
import name.zzhxufeng.wanandroid.ui.screens.drawer.items.NameLevelRank
import name.zzhxufeng.wanandroid.utils.*

@Composable
fun Coins(
    uiState: CoinUiState,
    handleEvent: (CoinEvent) -> Unit,
    navigateBack: () -> Unit,
    navigateToCheckInRecord: () -> Unit,
    navigateToHelp: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = SCREEN_PADDING.dp)
    ) {
        CoinsTopBar(
            desc = stringResource(R.string.title_coin_rank),
            navigateBack = navigateBack,
            navigateToHelp = navigateToHelp,
            navigateToCheckInRecord = navigateToCheckInRecord
        )
        if (uiState.myRank != null) {
            NameLevelRank(
                name = uiState.myRank.username,
                level = uiState.myRank.level,
                rank = uiState.myRank.rank
            )
        }

        Spacer(modifier = Modifier.height(ITEM_PADDING.dp))

        LazyColumn {
            item {
                uiState.myRank?.let {
                    RankItem(uiState.myRank)
                }
            }
            itemsIndexed(uiState.rankUiState.ranks) { index, item ->
                RankItem(item)

                LaunchedEffect(key1 = uiState.rankUiState.nextPage, block = {
                    Log.d("Coins", "recomposition...")
                    /*
                    * 每次新显示内容都会recomposition一次，
                    * 在 == 3 的时候加载下*一*页的内容
                    * */
                    if (uiState.rankUiState.ranks.size - index == 3) {
                        Log.d("Coins", "recomposition and load more...")
                        handleEvent(CoinEvent.LoadMoreRankList)
                    }
                })
            }
        }
    }
}

@Composable
fun RankItem(
    rankModel: RankModel
) {
    BorderedItemColumn {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "No. ${rankModel.rank}\t\t${rankModel.username}",
                fontSize = TEXT_FONT_SMALL.sp
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Lv. ${rankModel.level}\t\t|\t\t积分 ${rankModel.coinCount}",
                fontSize = TEXT_FONT_SMALL.sp,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
private fun CoinsTopBar(
    desc: String? = null,
    navigateBack: () -> Unit,
    navigateToCheckInRecord: () -> Unit,
    navigateToHelp: () -> Unit,
) {
    WanTopBar(
        desc = desc,
        backIcon = Icons.Default.ArrowBack,
        onBackClick = navigateBack,
    ){
        Row {
            Image(
                imageVector = Icons.Default.ReceiptLong,
                contentDescription = stringResource(R.string.desc_coin_check_in_record),
                modifier = Modifier.clickable { navigateToCheckInRecord() }
            )
            Image(
                imageVector = Icons.Default.HelpCenter,
                contentDescription = stringResource(R.string.desc_help_web),
                modifier = Modifier.clickable { navigateToHelp() }
            )
        }
    }
}