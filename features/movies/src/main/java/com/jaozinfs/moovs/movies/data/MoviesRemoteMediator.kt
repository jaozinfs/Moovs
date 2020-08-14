package com.jaozinfs.moovs.movies.data

//
//class MoviesRemoteMediator(
//    private val service: MoviesRepository,
//    private val repoDatabase: MoviesDatabase
//) : RemoteMediator<Int, MovieEntity>() {
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, MovieEntity>
//    ): MediatorResult {
//        val page = when (loadType) {
//            LoadType.REFRESH -> {
//                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
//                remoteKeys?.nextKey?.minus(1) ?: 1
//            }
//            LoadType.PREPEND -> {
//                val remoteKeys = getRemoteKeyForFirstItem(state)
//                if (remoteKeys == null) {
//                    // The LoadType is PREPEND so some data was loaded before,
//                    // so we should have been able to get remote keys
//                    // If the remoteKeys are null, then we're an invalid state and we have a bug
//                    throw InvalidObjectException("Remote key and the prevKey should not be null")
//                }
//                // If the previous key is null, then we can't request more data
//                val prevKey = remoteKeys.prevKey
//                if (prevKey == null) {
//                    return MediatorResult.Success(endOfPaginationReached = true)
//                }
//                remoteKeys.prevKey
//            }
//            LoadType.APPEND -> {
//                val remoteKeys = getRemoteKeyForLastItem(state)
//                if (remoteKeys == null || remoteKeys.nextKey == null) {
//                    throw InvalidObjectException("Remote key should not be null for $loadType")
//                }
//                remoteKeys.nextKey
//            }
//        } ?: 1
//
//        try {
//            val apiResponse = service.getMovies(page)
//
//
//            val endOfPaginationReached = apiResponse.isEmpty()
//            repoDatabase.withTransaction {
//                // clear all tables in the database
//                if (loadType == LoadType.REFRESH) {
//                    repoDatabase.moviesKeyIdDao().clearRemoteKeys()
//                    repoDatabase.moviesDao().clearRepos()
//                }
//                val prevKey = if (page == 1) null else page - 1
//                val nextKey = if (endOfPaginationReached) null else page + 1
//                val keys = apiResponse.map {
//                    MoviesKeyId(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
//                }
//                repoDatabase.moviesKeyIdDao().insertAll(keys)
//                repoDatabase.moviesDao().addAll(apiResponse)
//            }
//            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
//        } catch (exception: IOException) {
//            return MediatorResult.Error(exception)
//        } catch (exception: HttpException) {
//            return MediatorResult.Error(exception)
//        }
//    }
//
//    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): MoviesKeyId? {
//        // Get the last page that was retrieved, that contained items.
//        // From that last page, get the last item
//        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
//            ?.let { repo ->
//                // Get the remote keys of the last item retrieved
//                repoDatabase.moviesKeyIdDao().remoteKeysRepoId(repo.id)
//            }
//    }
//
//    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): MoviesKeyId? {
//        // Get the first page that was retrieved, that contained items.
//        // From that first page, get the first item
//        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
//            ?.let { repo ->
//                // Get the remote keys of the first items retrieved
//                repoDatabase.moviesKeyIdDao().remoteKeysRepoId(repo.id)
//            }
//    }
//
//    private suspend fun getRemoteKeyClosestToCurrentPosition(
//        state: PagingState<Int, MovieEntity>
//    ): MoviesKeyId? {
//        // The paging library is trying to load data after the anchor position
//        // Get the item closest to the anchor position
//        return state.anchorPosition?.let { position ->
//            state.closestItemToPosition(position)?.id?.let { repoId ->
//                repoDatabase.moviesKeyIdDao().remoteKeysRepoId(repoId)
//            }
//        }
//    }
//}