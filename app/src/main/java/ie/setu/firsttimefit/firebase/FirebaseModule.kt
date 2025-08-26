package ie.setu.firsttimefit.firebase

import android.app.Application
import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ie.setu.firsttimefit.R
import ie.setu.firsttimefit.firebase.auth.AuthRepository
import ie.setu.firsttimefit.firebase.database.FirestoreRepository
import ie.setu.firsttimefit.firebase.services.AuthService
import ie.setu.firsttimefit.firebase.services.FirestoreService

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideFirestoreRepository(
        auth: AuthService,
        firebaseFirestore: FirebaseFirestore
    ): FirestoreService = FirestoreRepository(
        auth = auth,
        firestore = firebaseFirestore
    )

    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth
    ): AuthService = AuthRepository(firebaseAuth = auth)

    @Provides
    fun provideCredentialManager(
        @ApplicationContext context: Context
    ) = CredentialManager.create(context)

    @Provides
    fun provideGoogleIdOptions(
        app: Application
    ) = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(app.getString(R.string.web_client_id))
        .build()

    @Provides
    fun getCredentialRequest(
        googleIdOption: GetGoogleIdOption
    ) = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()
}