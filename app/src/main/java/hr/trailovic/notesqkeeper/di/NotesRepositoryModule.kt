package hr.trailovic.notesqkeeper.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hr.trailovic.notesqkeeper.db.AppDatabase
import hr.trailovic.notesqkeeper.repo.NotesRepository
import hr.trailovic.notesqkeeper.repo.NotesRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NotesRepositoryModule {

    @Provides
    @Singleton
    fun provides(appDatabase: AppDatabase): NotesRepository = NotesRepositoryImpl(appDatabase)
}