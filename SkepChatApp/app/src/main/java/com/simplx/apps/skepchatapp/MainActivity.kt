package com.simplx.apps.skepchatapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView


class MainActivity : AppCompatActivity() {

    class MessageViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var messageTextView: TextView =
            itemView.findViewById<View>(R.id.messageTextView) as TextView
        var messageImageView: ImageView =
            itemView.findViewById<View>(R.id.messageImageView) as ImageView
        var messengerTextView: TextView =
            itemView.findViewById<View>(R.id.messengerTextView) as TextView
        var messengerImageView: CircleImageView =
            itemView.findViewById<View>(R.id.messengerImageView) as CircleImageView
    }

    private val TAG = "MainActivity"
    val MESSAGES_CHILD = "messages"
    private val REQUEST_INVITE = 1
    private val REQUEST_IMAGE = 2
    private val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
    val DEFAULT_MSG_LENGTH_LIMIT = 10
    val ANONYMOUS = "anonymous"

    private val MESSAGE_SENT_EVENT = "message_sent"
    private var mUsername: String? = null
    private var mPhotoUrl: String? = null
    private var mSharedPreferences: SharedPreferences? = null
    private var mSignInClient: GoogleSignInClient? = null
    private val MESSAGE_URL = "http://friendlychat.firebase.google.com/message/"

    private var mSendButton: Button? = null
    private var mMessageRecyclerView: RecyclerView? = null
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var mProgressBar: ProgressBar? = null
    private var mMessageEditText: EditText? = null
    private var mAddMessageImageView: ImageView? = null

    // Firebase instance variables
    private var mFirebaseAuth: FirebaseAuth? = null
    private var mFirebaseUser: FirebaseUser? = null

    private var mFirebaseDatabaseReference: DatabaseReference? = null
    private var mFirebaseAdapter: FirebaseRecyclerAdapter<Message, MessageViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        // Set default username is anonymous.
        mUsername = ANONYMOUS

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseUser = mFirebaseAuth!!.currentUser
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        } else {
            mUsername = mFirebaseUser?.displayName
            if (mFirebaseUser?.photoUrl != null) {
                mPhotoUrl = mFirebaseUser?.photoUrl.toString()
            }
        }

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mSignInClient = GoogleSignIn.getClient(this, gso)

        // Initialize ProgressBar and RecyclerView.
        mProgressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        mMessageRecyclerView = findViewById<View>(R.id.messageRecyclerView) as RecyclerView
        mLinearLayoutManager = LinearLayoutManager(this)
        mLinearLayoutManager?.stackFromEnd = true
        mMessageRecyclerView?.layoutManager = mLinearLayoutManager

        // mProgressBar?.visibility = ProgressBar.INVISIBLE
        getAllMessagesAndShowInRecyclerView()




        mMessageEditText = findViewById<View>(R.id.messageEditText) as EditText
        mMessageEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {

            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
                mSendButton?.isEnabled = charSequence.toString().trim { it <= ' ' }.isNotEmpty()
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })


        mSendButton = findViewById<View>(R.id.sendButton) as Button
        mSendButton?.setOnClickListener {

        }

        mAddMessageImageView = findViewById<View>(R.id.addMessageImageView) as ImageView
        mAddMessageImageView?.setOnClickListener {

        }
    }

    private fun getAllMessagesAndShowInRecyclerView() {

        // New child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().reference
        val parser: SnapshotParser<Message> =
            SnapshotParser<Message> { dataSnapshot ->
                val friendlyMessage: Message? = dataSnapshot.getValue(Message::class.java)
                friendlyMessage?.id = dataSnapshot.key.toString()
                friendlyMessage!!
            }

        val messagesRef = mFirebaseDatabaseReference!!.child(MESSAGES_CHILD)
        val options: FirebaseRecyclerOptions<Message> =
            FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(messagesRef, parser)
                .build()

        mFirebaseAdapter = object :
            FirebaseRecyclerAdapter<Message, MessageViewHolder>(
                options
            ) {
            override fun onCreateViewHolder(
                viewGroup: ViewGroup,
                i: Int
            ): MessageViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                return MessageViewHolder(
                    inflater.inflate(
                        R.layout.item_message,
                        viewGroup,
                        false
                    )
                )
            }

            override fun onBindViewHolder(
                viewHolder: MessageViewHolder,
                position: Int,
                friendlyMessage: Message
            ) {
                mProgressBar!!.visibility = ProgressBar.INVISIBLE
                viewHolder.messageTextView.text = friendlyMessage.text
                viewHolder.messageTextView.visibility = TextView.VISIBLE
                viewHolder.messageImageView.visibility = ImageView.GONE
                viewHolder.messengerTextView.text = friendlyMessage.text
                Glide.with(this@MainActivity)
                    .load(friendlyMessage.photoUrl)
                    .into(viewHolder.messengerImageView)
            }
        }

        mFirebaseAdapter?.registerAdapterDataObserver(
            object : AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    val friendlyMessageCount = mFirebaseAdapter?.itemCount
                    val lastVisiblePosition =
                        mLinearLayoutManager!!.findLastCompletelyVisibleItemPosition()

                    if (friendlyMessageCount != null) {
                        if (lastVisiblePosition == -1 || positionStart >= friendlyMessageCount - 1 &&
                            lastVisiblePosition == positionStart - 1
                        ) {
                            mMessageRecyclerView!!.scrollToPosition(positionStart)
                        }
                    }
                }
            })

        mMessageRecyclerView!!.adapter = mFirebaseAdapter
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onPause() {
        mFirebaseAdapter!!.stopListening()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mFirebaseAdapter!!.startListening()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sign_out_menu -> {
                mFirebaseAuth!!.signOut()
                mSignInClient!!.signOut()
                mUsername = ANONYMOUS
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}