package com.larro.plus

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.facebook.*
import com.facebook.login.LoginManager
import com.google.android.material.textview.MaterialTextView
import com.larro.plus.adapters.CardRecyclerViewAdapter
import com.larro.plus.callbacks.ProfileCallback
import com.larro.plus.models.Card
import com.larro.plus.net.facebook.ProfileRequest


class MainActivity : AppCompatActivity() {

    val fbCallbackManager : CallbackManager = CallbackManager.Factory.create()
    var profileCallback = ProfileCallback()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            LoginActivity.LOGIN_SUCESS -> getFbProfileInfo()
        }
        fbCallbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        setSupportActionBar(findViewById(R.id.app_bar))
       // setNextLevelUiInfo(0, "")
       // setLevelUiInfo(1978, "Platinum")
        val recyclerView = findViewById<RecyclerView>(R.id.reclycerView)
        recyclerView.adapter = CardRecyclerViewAdapter(createSampleCards())
        recyclerView.setHasFixedSize(true)

        LoginManager.getInstance().retrieveLoginStatus(applicationContext,
            object : LoginStatusCallback {
                override fun onCompleted(accessToken: AccessToken?) {
                    getFbProfileInfo()
                    // TODO call setNextLevelUiInfo with parameters
                   // setNextLevelUiInfo(78, "Premium")
                    Toast.makeText(
                        applicationContext, "LoginStatusCallback onCompleted",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure() {
                    if (AccessToken.getCurrentAccessToken() == null) {
                        val loginIntent = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(loginIntent)
                        Toast.makeText(
                            applicationContext, "LoginStatusCallback onFailure",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        getFbProfileInfo()
                        // TODO call setNextLevelUiInfo with parameters
                       // setNextLevelUiInfo(78, "Premium")
                        Toast.makeText(
                            applicationContext, "LoginStatusCallback onFailure called, " +
                                    "but we have access token",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(exception: Exception?) {
                    Toast.makeText(
                        applicationContext, "LoginStatusCallback onError",
                        Toast.LENGTH_SHORT
                    ).show()
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
        return ProfileRequest().makeProfileRequest(profileCallback)
    }

    private fun onLogoutClicked() {
        LoginManager.getInstance().logOut();
        startActivity(Intent(applicationContext, LoginActivity::class.java))
    }

    private fun setNextLevelUiInfo(points : Int, nextLevel : String) {
        var nextLevelText = findViewById<MaterialTextView>(R.id.next_level_text)
        nextLevelText.text = getString(R.string.next_level_points, points, nextLevel)
    }

    private fun setLevelUiInfo(points : Int, level : String) {
        var headerText = findViewById<MaterialTextView>(R.id.header)
        headerText.text = getString(R.string.points_header, level)
        var pointsText = findViewById<MaterialTextView>(R.id.body)
        pointsText.text = points.toString()
    }

    // TODO remove this
    private fun createSampleCards() : List<Card> {
        val card1 = Card("Seu dinheiro vale mais", "Você tem 5% de desconto para " +
                "pagamento em dinheiro")
        val card2 = Card("10% OFF", "Use seu cupom de 10% de desconto em qualquer " +
                "item para compras acima de R$ 100")
        val card3 = Card("Ofertas exclusivas!", "Veja nossas ofertas exclusivas para " +
                "membros Larrô+")
        val card4 = Card("Mais Desconto!!!", "15% off para compras acimas de R$ 200.")
        return listOf<Card>(card1, card2, card3, card4)

    }

}