@file:Suppress("unused")

package de.dertyp7214.rboardthememanager.components

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import de.dertyp7214.rboardthememanager.R

@SuppressLint("ResourceType")
class SearchBar(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    var focus = false
        private set
    private var searchListener: (text: String) -> Unit = {}
    private var closeListener: () -> Unit = {}
    private var focusListener: () -> Unit = {}

    @MenuRes
    private var menuId: Int = -1
    private var menuItemClickListener: PopupMenu.OnMenuItemClickListener? = null


    private val searchBar: CardView
    private val searchButton: ImageButton
    private val backButton: ImageButton
    private val moreButton: ImageButton
    private val searchText: TextView
    private val searchEdit: EditText

    init {
        inflate(context, R.layout.search_bar, this)

        searchBar = findViewById(R.id.search_bar)

        searchButton = findViewById(R.id.search_button)
        backButton = findViewById(R.id.back_button)
        moreButton = findViewById(R.id.more_button)

        searchText = findViewById(R.id.search_text)
        searchEdit = findViewById(R.id.search)

        searchBar.setOnClickListener {
            if (!focus) {
                focus = true
                searchButton.visibility = GONE
                backButton.visibility = VISIBLE

                searchText.visibility = GONE
                searchEdit.visibility = VISIBLE

                searchEdit.requestFocus()
                val imm: InputMethodManager =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(searchEdit, InputMethodManager.SHOW_IMPLICIT)
                focusListener()
            }
        }

        backButton.setOnClickListener {
            if (focus) {
                focus = false
                searchButton.visibility = VISIBLE
                backButton.visibility = GONE

                searchText.visibility = VISIBLE
                searchEdit.visibility = GONE

                searchEdit.setText("")
                clearFocus(searchEdit)
                closeListener()
            }
        }

        moreButton.setOnClickListener {
            if (menuId > 0) {
                val popupMenu = PopupMenu(context, it)
                popupMenu.menuInflater.inflate(menuId, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(menuItemClickListener)
                popupMenu.show()
            }
        }

        searchEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                clearFocus(searchEdit)
                searchListener(searchEdit.text.toString())
                true
            } else false
        }
    }

    fun setMenu(
        @MenuRes menu: Int = -1,
        itemClickListener: PopupMenu.OnMenuItemClickListener? = null
    ) {
        menuId = menu
        menuItemClickListener = itemClickListener
    }

    fun setText(text: String = "") {
        if (text.isEmpty()) {
            focus = false
            searchButton.visibility = VISIBLE
            backButton.visibility = GONE

            searchText.visibility = VISIBLE
            searchEdit.visibility = GONE
        }

        searchEdit.setText(text)
        clearFocus(searchEdit)
    }

    fun setOnSearchListener(listener: (text: String) -> Unit) {
        searchListener = listener
    }

    fun setOnCloseListener(listener: () -> Unit) {
        closeListener = listener
    }

    fun setOnFocusListener(listener: () -> Unit) {
        focusListener = listener
    }

    override fun clearFocus() {
        clearFocus(searchEdit)
    }

    private fun clearFocus(editText: EditText) {
        editText.clearFocus()
        val imm: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}