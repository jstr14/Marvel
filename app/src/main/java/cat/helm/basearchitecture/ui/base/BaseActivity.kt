package cat.helm.basearchitecture.ui.base

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection

/**
 * Created by Borja on 21/12/16.
 */

abstract class BaseActivity : AppCompatActivity(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState)
        setContentView(onRequestLayout())
    //    initializeInjection()
        onViewLoaded()
    }

    abstract fun onRequestLayout(): Int

    fun initializeInjection() {
     //   val component = (application as Application).component.plus(ActivityModule(this), ViewModule(this))
      //  injectActivity(component)
    }

  //  abstract fun injectActivity(component: ActivityComponent)

    abstract fun onViewLoaded()

     override fun showException(exceptionMessage: String) {
        Snackbar.make(findViewById(android.R.id.content), exceptionMessage, Snackbar.LENGTH_LONG).show()
    }

}
