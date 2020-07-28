package io.aequalis.lava.utils

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.*
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.telephony.PhoneNumberUtils
import android.text.TextUtils
import android.text.format.DateUtils
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import io.aequalis.lava.R
import org.json.JSONException
import org.json.JSONObject
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class UserInterface {
    companion object {
        var rateUsDialog: Dialog? = null
        private var progressDialog: Dialog? = null
        private val CONTACT_PERMISSIONS = arrayOf(Manifest.permission.READ_CONTACTS)
        private var snack: Snackbar? = null
        //Show Progress bar
        fun showProgress(message: String, context: Context?) {
            if (context != null) {
                try {
                    if (!isShowing()) {
                        progressDialog = Dialog(context, R.style.CustomDialog)
                        progressDialog!!.setContentView(R.layout.custom_progress_dialog)
                        val d = ColorDrawable(Color.TRANSPARENT)
                        progressDialog!!.window!!.setBackgroundDrawable(d)
                        progressDialog!!.setCancelable(false)
                        val msg = progressDialog!!.findViewById<TextView>(R.id.msg)
                        msg.text = message
                        progressDialog!!.show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

        //Show Progress bar
        fun showCancellableProgress(message: String, context: Context?) {
            if (context != null) {
                try {
                    if (!isShowing()) {
                        progressDialog = Dialog(context, R.style.CustomDialog)
                        progressDialog!!.setContentView(R.layout.custom_progress_dialog)
                        val d = ColorDrawable(Color.TRANSPARENT)
                        progressDialog!!.window!!.setBackgroundDrawable(d)
                        progressDialog!!.setCancelable(true)
                        val msg = progressDialog!!.findViewById<TextView>(R.id.msg)
                        msg.text = message
                        progressDialog!!.show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

        fun getCurrentDateTime(): String {
            try {
                val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm aa", Locale.getDefault())
                return sdf.format(Date())
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }

        fun setTypeface(tf: Typeface, v: View) {
            when (v) {
                is TextView -> v.typeface = tf
                is EditText -> v.typeface = tf
                is AppCompatTextView -> v.typeface = tf
                is AppCompatEditText -> v.typeface = tf
                is TextInputLayout -> v.typeface = tf
                is ViewGroup -> for (i in 0 until v.childCount) {
                    setTypeface(tf, v.getChildAt(i))
                }
            }
        }

        fun isShowing(): Boolean {
            return if (progressDialog != null)
                progressDialog!!.isShowing()
            else
                false
        }

        //Hide Progress bar
        fun hideProgress(context: Context) {
            try {
                if (progressDialog != null) {
                    if (progressDialog!!.isShowing())
                        progressDialog!!.dismiss()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }



        fun dismissSnack() {
            if (snack != null)
                snack!!.dismiss()
        }


        fun showToast(context: Context?, message: String) {
            if (context != null) {
                val inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val layout = inflater.inflate(R.layout.custom_toast, null, false)
                val image = layout.findViewById(R.id.image) as ImageView
                image.setImageResource(R.drawable.ic_tick_white)
                val text = layout.findViewById(R.id.msg) as TextView
                text.text = message
                val toast = Toast(context)
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                toast.duration = Toast.LENGTH_SHORT
                toast.view = layout
                toast.show()
            }
        }

        fun showOKAlert(
            c: Context?,
            message: String,
            cancelable: Boolean,
            listener: DialogInterface.OnClickListener
        ) {
            // TODO Auto-generated constructor stub
            if (c != null) {
                val b = AlertDialog.Builder(c)
                b.setTitle(c.resources.getString(R.string.error))
                b.setMessage(message)
                b.setPositiveButton(c.resources.getString(R.string.ok), listener)
                b.setCancelable(cancelable)
                val d = b.create()
                d.show()
            }
        }


        fun showOKTitleAlert(
            c: Context?,
            title: String,
            message: String,
            cancelable: Boolean,
            listener: DialogInterface.OnClickListener
        ) {
            // TODO Auto-generated constructor stub
            if (c != null) {
                val b = AlertDialog.Builder(c)
                b.setTitle(title)
                b.setMessage(message)
                b.setPositiveButton("OK", listener)
                b.setCancelable(cancelable)
                val d = b.create()
                d.show()
            }
        }

        fun showLowCreditsAlert(
            c: Context?,
            title: String,
            message: String,
            cancelable: Boolean,
            positiveButton: String,
            negativeButton: String,
            positiveListener: DialogInterface.OnClickListener
        ) {
            // TODO Auto-generated constructor stub
            if (c != null) {
                var d: AlertDialog? = null
                val b = AlertDialog.Builder(c)
                b.setTitle(title)
                b.setMessage(message)
                b.setPositiveButton(positiveButton, positiveListener)
                b.setNegativeButton(negativeButton) { dialogInterface, which ->
                    // d.dismiss()
                }
                b.setCancelable(cancelable)
                d = b.create()
                d.show()

            }
        }



        fun showOkCancelAlert(
            c: Context?,
            title: String,
            message: String,
            okListener: DialogInterface.OnClickListener,
            okListenerNegative: DialogInterface.OnClickListener
        ) {
            // TODO Auto-generated constructor stub
            if (c != null) {
                val b = AlertDialog.Builder(c)
                b.setTitle(title)
                b.setMessage(message)
                b.setPositiveButton("OK", okListener)
                b.setNegativeButton("Cancel", okListenerNegative)
                val d = b.create()
                d.show()
                val titleView = d.findViewById<TextView>(
                    c.resources.getIdentifier(
                        "alertTitle",
                        "id",
                        "android"
                    )
                )
                if (titleView != null) {
                    titleView.gravity = Gravity.CENTER_HORIZONTAL
                }
            }
        }

        fun hideKeyboard(context: Context, view: View) {
            val `in` = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            `in`.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

        // Check Email Id is Valid or Not
        fun isValidEmail(target: CharSequence): Boolean {
            return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }

        fun parseDateWithTimeYear(time: String): String {
            val incomingFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val outGoingFormat = SimpleDateFormat("dd MMM yyyy    hh:mm aa")
            try {
                val date = incomingFormat.parse(time)
                return outGoingFormat.format(date!!)
            } catch (pe: ParseException) {
                pe.printStackTrace()
            }

            return ""
        }




        fun getParseTime(date: Date): String {
            val outGoingFormat = SimpleDateFormat("dd MMM yyyy")
            try {
                return outGoingFormat.format(date)
            } catch (pe: Exception) {
                pe.printStackTrace()
            }

            return ""
        }

        fun formateddate(dateString: String): String {
            try {
                val incomingFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val date = incomingFormat.parse(dateString)
                return when {
                    isToday(date) -> parseDate(date)
                    isYesterday(date) -> "Yesterday"
                    else -> parseDate(
                        date
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }

        fun isToday(date: Date): Boolean {
            return DateUtils.isToday(date.time)
        }

        fun isYesterday(d: Date): Boolean {
            return DateUtils.isToday(d.time + DateUtils.DAY_IN_MILLIS)
        }

        fun parseDate(date: Date): String {
            val outGoingFormat = SimpleDateFormat("MMM dd")
            try {
                return outGoingFormat.format(date)
            } catch (pe: Exception) {
                pe.printStackTrace()
            }

            return ""
        }

        fun getChatTime(date: Date): String {
            val outGoingFormat = SimpleDateFormat("hh:mm aa")
            try {
                return outGoingFormat.format(date)
            } catch (pe: Exception) {
                pe.printStackTrace()
            }

            return ""
        }

        fun getChatDate(date: Date): String {
            val outGoingFormat = SimpleDateFormat("dd MMM yyyy")
            try {
                return outGoingFormat.format(date)
            } catch (pe: Exception) {
                pe.printStackTrace()
            }

            return ""
        }

        fun getTime(date: Date): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
            try {
                val c = Calendar.getInstance()
                c.time = date!!
                c.timeZone = TimeZone.getDefault()
                return convertToHumanReadable(c.timeInMillis, date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return ""
        }

        fun getTime(started: String): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
            try {
                val date = simpleDateFormat.parse(started)
                val c = Calendar.getInstance()
                c.time = date!!
                c.timeZone = TimeZone.getDefault()
                return convertToHumanReadable(c.timeInMillis, date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return ""
        }

        private fun convertToHumanReadable(milliseconds: Long, date: Date?): String {
            val today = Calendar.getInstance()
            val postedDay = Calendar.getInstance()
            postedDay.timeInMillis = milliseconds
            val day = 86400000L
            val hour = 3600000L
            val minute = 60000L
            val month = 2628000000L
            val year = 31536000000L
            val time: Int

            return when {
                isToday(date!!) -> {
                    when {
                        today.timeInMillis - postedDay.timeInMillis > hour -> {
                            time = ((today.timeInMillis - postedDay.timeInMillis) / hour).toFloat()
                                .roundToInt()
                            time.toString() + if (time == 1) "h" else "h"
                        }
                        else -> {
                            time =
                                ((today.timeInMillis - postedDay.timeInMillis) / minute).toFloat()
                                    .roundToInt()
                            if (time == 0) "Now" else time.toString() + "m"
                        }
                    }
                }
                isYesterday(date) -> "1 Day"
                else -> parseDate(
                    date
                )
            }

        }

        private fun convertToHumanReadable(milliseconds: Long): String {
            val today = Calendar.getInstance()
            val postedDay = Calendar.getInstance()
            postedDay.timeInMillis = milliseconds
            val day = 86400000L
            val hour = 3600000L
            val minute = 60000L
            val month = 2628000000L
            val year = 31536000000L
            val time: Int

            if (today.timeInMillis - postedDay.timeInMillis > year) {
                time = Math.round(((today.timeInMillis - postedDay.timeInMillis) / year).toFloat())
                return time.toString() + if (time == 1) "yr" else "yr"
            } else if (today.timeInMillis - postedDay.timeInMillis > month) {
                time = Math.round(((today.timeInMillis - postedDay.timeInMillis) / month).toFloat())
                return time.toString() + if (time == 1) "mo" else "mo"
            } else if (today.timeInMillis - postedDay.timeInMillis > day) {
                time = Math.round(((today.timeInMillis - postedDay.timeInMillis) / day).toFloat())
                return time.toString() + if (time == 1) "Day" else "Day"
            } else if (today.timeInMillis - postedDay.timeInMillis > hour) {
                time = Math.round(((today.timeInMillis - postedDay.timeInMillis) / hour).toFloat())
                return time.toString() + if (time == 1) "h" else "h"
            } else {
                time =
                    Math.round(((today.timeInMillis - postedDay.timeInMillis) / minute).toFloat())
                return time.toString() + if (time == 1) " m" else " m"
            }
        }

        private fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
                for (permission in permissions) {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            permission
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return false
                    }
                }
            }
            return true
        }



    }


}