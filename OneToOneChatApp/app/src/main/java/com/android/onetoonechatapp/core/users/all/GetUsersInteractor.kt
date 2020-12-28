package com.android.onetoonechatapp.core.users.all

import android.text.TextUtils
import com.android.onetoonechatapp.core.users.all.GetUsersContract.OnGetAllUsersListener
import com.android.onetoonechatapp.models.User
import com.android.onetoonechatapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class GetUsersInteractor(private val mOnGetAllUsersListener: OnGetAllUsersListener) :
    GetUsersContract.Interactor {

    override fun getAllUsersFromFirebase() {
        FirebaseDatabase.getInstance().reference.child(Constants.ARG_USERS)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val dataSnapshots: Iterator<DataSnapshot> = dataSnapshot.children.iterator()
                    val users: MutableList<User?> = ArrayList()
                    while (dataSnapshots.hasNext()) {
                        val dataSnapshotChild = dataSnapshots.next()
                        val user = dataSnapshotChild.getValue(
                            User::class.java
                        )
                        if (!TextUtils.equals(
                                user!!.uid,
                                FirebaseAuth.getInstance().currentUser!!.uid
                            )
                        ) {
                            users.add(user)
                        }
                    }
                    mOnGetAllUsersListener.onGetAllUsersSuccess(users)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    mOnGetAllUsersListener.onGetAllUsersFailure(databaseError.message)
                }
            })
    }

    override fun getChatUsersFromFirebase() {
        /*
        FirebaseDatabase.getInstance().getReference().child(Constants.ARG_CHAT_ROOMS).addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              Iterator<DataSnapshot> dataSnapshots=dataSnapshot.getChildren().iterator();
              List<User> users=new ArrayList<>();
              while (dataSnapshots.hasNext()){
                  DataSnapshot dataSnapshotChild=dataSnapshots.next();
                  dataSnapshotChild.getRef().
                  Chat chat=dataSnapshotChild.getValue(Chat.class);
                  if(chat.)4
                  if(!TextUtils.equals(user.uid,FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                      users.add(user);
                  }
              }
          }
          @Override
          public void onCancelled(DatabaseError databaseError) {
          }
      });
      */
    }
}