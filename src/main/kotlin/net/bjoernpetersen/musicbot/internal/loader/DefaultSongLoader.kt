package net.bjoernpetersen.musicbot.internal.loader

import net.bjoernpetersen.musicbot.api.Song
import net.bjoernpetersen.musicbot.spi.loader.SongLoader
import net.bjoernpetersen.musicbot.spi.plugin.Provider
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DefaultSongLoader @Inject private constructor() : SongLoader {
    private val executor: ExecutorService = Executors.newFixedThreadPool(2)
    override fun startLoading(provider: Provider, song: Song): Future<Boolean> {
        return executor.submit(Callable<Boolean> { provider.loadSong(song) })
    }

    override fun close() {
        executor.shutdown()
        executor.awaitTermination(5, TimeUnit.SECONDS)
    }
}