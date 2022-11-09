package DataSource

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagergallery.modes.Resultd
import com.example.pagergallery.modes.listDatas
import com.example.pagergallery.network.RetrofitApi
import retrofit2.HttpException
import java.io.IOException

class PagingSourced(val apiService: RetrofitApi) : PagingSource<Int, Resultd>() {
    override fun getRefreshKey(state: PagingState<Int, Resultd>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Resultd> {
        return try {
            val nextPageNumber :Int= params?.key ?: 1
            val response = apiService.getDatas(nextPageNumber)
            var nextPage:Int?=null
            if (response?.info?.next != null) {

                val uri = Uri.parse(response?.info?.next!!)
                var nextPageQ : String? = uri.getQueryParameter("page")
                nextPage = nextPageQ?.toInt()!!

            }
            return LoadResult.Page(
                data = response.results,
                prevKey = null, // Only paging forward.
                nextKey = nextPage
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }
}