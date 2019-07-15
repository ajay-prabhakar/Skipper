package chromicle.mvmm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import chromicle.mvmm.data.db.entitives.User
import chromicle.mvmm.data.db.entitives.UserDao

/**
 *Created by Chromicle on 15/7/19.
 */

@Database(entities = [User :: class],version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getUserData() : UserDao

    companion object{

        @Volatile
        private var instance: AppDataBase? = null
        private val LOCK = Any()



        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "MyDatabase.db"
            ).build()

    }
}