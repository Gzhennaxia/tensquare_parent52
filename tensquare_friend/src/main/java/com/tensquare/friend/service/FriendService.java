package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {
    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    public int addFriend(String userid, String friendid) {
        // 先判断userid到friendid是否有数据，有就代表重复添加
        Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
        if (friend != null) {
            return 0;
        }
        // 直接添加好友，让好友表中userid到friendid的islike等于0
        friend = Friend.builder().userid(userid).friendid(friendid).islike("0").build();
        friendDao.save(friend);
        // 判断从friendid到userid是否有数据，如果有，把双方的islike都改为1
        if (friendDao.findByUseridAndFriendid(friendid, userid) != null) {
            friendDao.updateIsLike("1", userid, friendid);
            friendDao.updateIsLike("1", friendid, userid);
        }
        return 1;
    }

    public int addNoFriend(String userid, String friendid) {
        // 先判断userid到friendid是否有数据，有就代表重复添加
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userid, friendid);
        if (noFriend != null) {
            return 0;
        }
        // 直接添加非好友
        noFriend = NoFriend.builder().userid(userid).friendid(friendid).build();
        noFriendDao.save(noFriend);
        return 1;
    }
}
