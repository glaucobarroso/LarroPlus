package com.larro.plus.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.facebook.*
import com.facebook.login.LoginManager
import com.larro.plus.R
import com.larro.plus.adapters.MainRecyclerViewAdapter
import com.larro.plus.callbacks.ProfileCallback
import com.larro.plus.models.Card
import com.larro.plus.models.UserInfo
import com.larro.plus.net.facebook.ProfileRequest

const val PROFILE_MSG = 0

class MainActivity : AppCompatActivity() {

    var handler : Handler? = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when(msg?.what) {
                PROFILE_MSG -> dismissLoadingProgressBar()
            }
        }
    }
    private val fbCallbackManager : CallbackManager = CallbackManager.Factory.create()
    var profileCallback = ProfileCallback(handler)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            LOGIN_SUCESS -> {
                getFbProfileInfo()
            }
        }
        fbCallbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        setSupportActionBar(findViewById(R.id.app_bar))

        LoginManager.getInstance().retrieveLoginStatus(applicationContext,
            object : LoginStatusCallback {
                override fun onCompleted(accessToken: AccessToken?) {
                    getFbProfileInfo()
                }

                override fun onFailure() {
                    if (AccessToken.getCurrentAccessToken() == null) {
                        val loginIntent = Intent(applicationContext, LoginActivity::class.java)
                        startActivityForResult(loginIntent, 0)
                    } else {
                        getFbProfileInfo()
                    }
                }

                override fun onError(exception: Exception?) {
                }
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                onLogoutClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getFbProfileInfo() {
        Thread { ProfileRequest().makeProfileRequest(profileCallback) }.start()
    }

    private fun onLogoutClicked() {
        LoginManager.getInstance().logOut()
        startActivityForResult(Intent(applicationContext, LoginActivity::class.java), 0)
    }

    private fun dismissLoadingProgressBar() {
        val loadingProgressBar = findViewById<ProgressBar>(R.id.progress_loader)
        val uiAfterLoading = findViewById<RecyclerView>(R.id.recycler_view)
        uiAfterLoading.adapter = MainRecyclerViewAdapter(
            createSampleCards(),
            createSampleUserInfo()
        )
        uiAfterLoading.setHasFixedSize(true)
        loadingProgressBar.visibility = View.GONE
        uiAfterLoading.visibility = View.VISIBLE
    }

    // TODO remove this
    private fun createSampleCards() : List<Card> {
        val card1 = Card(
            "Seu dinheiro vale mais", "Você tem 5% de desconto para " +
                    "pagamento em dinheiro", "5% de desconto em dinheiro"
        )
        val card2 = Card(
            "10% OFF", "Use seu cupom de 10% de desconto em qualquer " +
                    "item para compras acima de R$ 100",  "10% OFF acima de R$ 100"
        )
        val card3 = Card(
            "Ofertas exclusivas!", "Veja nossas ofertas exclusivas para " +
                    "membros Larrô+", "Ofertas Exclusivas"
        )
        val card4 = Card("Mais Desconto!!!", "15% off para compras acimas de R$ 200.",
            "15% OFF acima de R$ 200")
        return listOf(card1, card2, card3, card4)

    }

    // TODO remove this
    private fun createSampleUserInfo() : UserInfo = UserInfo(
        "id", "User", 1978,
        "Platinum", "Platinum Black", 2000
    )

}