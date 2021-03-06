package models;

import com.google.gson.annotations.SerializedName;
import controller.InstaSalle;
import datastructures.ElementWithStringKey;
import datastructures.HashTable.HashTable;
import datastructures.LinkedList.LinkedList;


/**
 * This class contains the details of the user.
 * Note that annotations (@SerializedName) can be used to respect lowerCamelCase notation in Java.
 *
 * @see com.google.gson.annotations
 *
 * @author Cristian, Ferran, Iscle
 *
 */
public class User implements ElementWithStringKey {

    private String username;
    private int creation;

    @SerializedName("to_follow")
    private String[] toFollowUsernames;

    // Graph
    private transient LinkedList<User> following;
    private transient LinkedList<User> followers;
    private transient LinkedList<Post> posts;
    private transient LinkedList<Post> likedPosts;

    public User() {
        this.following = new LinkedList<>();
        this.followers = new LinkedList<>();
        this.posts = new LinkedList<>();
        this.likedPosts = new LinkedList<>();
    }

    public User(String username) {
        this.following = new LinkedList<>();
        this.followers = new LinkedList<>();
        this.posts = new LinkedList<>();
        this.likedPosts = new LinkedList<>();
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCreation() {
        return creation;
    }

    public void setCreation(int creation) {
        this.creation = creation;
    }

    public String[] getToFollowUsernames() {
        return toFollowUsernames;
    }

    public void setToFollowUsernames(String[] toFollowUsernames) {
        this.toFollowUsernames = toFollowUsernames;
    }

    public LinkedList<User> getFollowing() {
        return following;
    }

    public void setFollowing(LinkedList<User> following) {
        this.following = following;
    }

    public LinkedList<User> getFollowers() {
        return followers;
    }

    public void setFollowers(LinkedList<User> followers) {
        this.followers = followers;
    }

    public LinkedList<Post> getPosts() {
        return posts;
    }

    public void setPosts(LinkedList<Post> posts) {
        this.posts = posts;
    }

    public LinkedList<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(LinkedList<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    // Two users are equal if their usernames are equal
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username == user.username;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public String toString() {

        StringBuilder sb1 = new StringBuilder();
        LinkedList<User> linkedList1 = this.getFollowing();
        User[] array1 = linkedList1.toArray(new User[linkedList1.getSize()]);
        for (User user: array1) {
            sb1.append('\n');
            sb1.append(user.toLittleString());
        }

        StringBuilder sb2 = new StringBuilder();
        LinkedList<User> linkedList2 = this.getFollowers();
        User[] array2 = linkedList2.toArray(new User[linkedList2.getSize()]);
        for (User user: array2) {
            sb2.append('\n');
            sb2.append(user.toLittleString());
        }

        StringBuilder sb3 = new StringBuilder();
        LinkedList<Post> linkedList3 = this.getPosts();
        Post[] array3 = linkedList3.toArray(new Post[linkedList3.getSize()]);
        for (Post post: array3) {
            sb3.append('\n');
            sb3.append(post.toLittleString());
        }

        StringBuilder sb4 = new StringBuilder();
        LinkedList<Post> linkedList4 = this.getLikedPosts();
        Post[] array4 = linkedList4.toArray(new Post[linkedList4.getSize()]);
        for (Post post: array4) {
            sb4.append('\n');
            sb4.append(post.toLittleString());
        }

        return "User {" + '\n' +
                "username='" + username + ", " + '\n' +
                "creation=" + creation + ", " + '\n' +
                "following=[" + sb1.toString().replace("\n", "\n\t") + '\n' + "]" + ", " + '\n' +
                "followers=[" + sb2.toString().replace("\n", "\n\t") + '\n' + "]" + ", " + '\n' +
                "posts=[" + sb3.toString().replace("\n", "\n\t") + '\n' + "]" + ", " + '\n' +
                "liked_posts=[" + sb4.toString().replace("\n", "\n\t") + '\n' + "]" + ", " + '\n' +
                '}';
    }

    public String toLittleString() {
        return "User {" + '\'' +
                "username='" + username + ", " + '\n' +
                "creation=" + creation + ", " + '\n' +
                '}';
    }

    @Override
    public String getKey() {
        return this.username;
    }

    public void fillFromUserInput(HashTable<User> usersByUsername, HashTable<Post> postsById) {


        while (true) {
            System.out.println("Username:");
            String desiredUsername = InstaSalle.scanner.nextLine();
            User user = (User) usersByUsername.get(desiredUsername);
            if (user != null) {
                System.out.println("User with username " + desiredUsername + " already exists... Please, try again.");
            }
            else {
                this.username = desiredUsername;
                break;
            }
        }

        System.out.println("Creation timestamp:");
        this.creation = Integer.parseInt(InstaSalle.scanner.nextLine());

        System.out.println("N of users to follow:");
        int length = Integer.parseInt(InstaSalle.scanner.nextLine());
        this.toFollowUsernames = new String[length];

        for (int i = 0; i < length; i++) {
            while (true) {
                System.out.println("Username of user " + (i + 1) + " to follow:");
                String desiredUsername = InstaSalle.scanner.nextLine();
                User author = (User) usersByUsername.get(desiredUsername);
                if (author == null) {
                    System.out.println("Username " + desiredUsername + " has not been found... Please, try again.");
                }
                else {
                    this.toFollowUsernames[i] = desiredUsername;
                    break;
                }
            }
        }
    }
}
