package com.taras.movieapp.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

fun <T> CoroutineScope.asyncIO(async_io: () -> T) = async(Dispatchers.IO) { async_io }