package controller;

import com.google.gson.JsonIOException;
import datastructures.AVLTree.AVLTree;
import models.Hashtag;
import models.Post;
import models.User;
import utils.JsonReader;

import java.io.FileNotFoundException;
import java.util.*;

public class InstaSalle {
    private Scanner kb;
    private User[] users;
    private Post[] posts;

    public InstaSalle() throws JsonIOException, FileNotFoundException {
        this.parseData();
    }

    private void parseData() throws JsonIOException, FileNotFoundException {
        this.users = JsonReader.parseUsers();
        this.posts = JsonReader.parsePosts();

        HashMap<String, User> usersByUsername = new HashMap<>();
        for (User user: this.users) {
            usersByUsername.put(user.getUsername(), user);
        }
        for (User user: this.users) {
            for (String usernameToFollow: user.getToFollowUsernames()) {
                User toFollow = usersByUsername.get(usernameToFollow);
                user.getFollowing().add(toFollow);
                toFollow.getFollowers().add(user);
            }
        }

        HashMap<String, Hashtag> hashtagsByName = new HashMap<>();
        for (Post post: this.posts) {
            // Post liked by...
            for (String usernameWhoLiked: post.getLikedByUsernames()) {
                User likedBy = usersByUsername.get(usernameWhoLiked);
                post.getLikedBy().add(likedBy);
                likedBy.getLikedPosts().add(post);
            }
            // Post published by...
            User author = usersByUsername.get(post.getPublishedByUsername());
            author.getPosts().add(post);
            post.setPublishedBy(author);

            // Post with hashtags...
            for (String hashtagId: post.getHashtagIds()) {
                Hashtag hashtag = hashtagsByName.get(hashtagId);
                if (hashtag == null) {
                    hashtag = new Hashtag(hashtagId);
                    hashtagsByName.put(hashtagId, hashtag);
                }
                hashtag.getPosts().add(post);
                post.getHashtags().add(hashtag);
            }
        }
    }

    public void showFunctionalitiesMenu() {
        System.out.println("---------- InstaSalle ------------");
        System.out.println("0. Print read data of JSON");
        System.out.println("1. Import files");
        System.out.println("2. Export files");
        System.out.println("3. AVL Tree");
        System.out.println("Select an option from the menu:");
    }

    /**
     * Start one of the application options.
     *
     * @param functionalityOption The selected functionality.
     */
    private void handleOption(int functionalityOption) {

        switch (functionalityOption) {

            case 0:

                for (User user: this.users) {
                    System.out.println(user);
                }

                for (Post post: this.posts) {
                    System.out.println(post);
                }

                break;

            case 1:
                break;

            case 2:
                break;

            case 3:
                AVLTree tree = new AVLTree();

        /* Constructing tree given in the above figure
                tree.setRoot(tree.insert(tree.getRoot(), 10));
                tree.setRoot(tree.insert(tree.getRoot(), 20));
                tree.setRoot(tree.insert(tree.getRoot(), 30));
                tree.setRoot(tree.insert(tree.getRoot(), 40));
                tree.setRoot(tree.insert(tree.getRoot(), 50));
                tree.setRoot(tree.insert(tree.getRoot(), 25));*/

        /* The constructed AVL Tree would be
             30
            /  \
          20   40
         /  \     \
        10  25    50
        */


                for (Post post: this.posts) {
                    System.out.println("adding post: " + post);
                    System.out.println(post);
                    tree.setRoot(tree.insert(tree.getRoot(), post));
                }

                System.out.println("Preorder traversal" +
                        " of constructed tree is : ");
                tree.preOrder(tree.getRoot());

                System.out.println("");
                System.out.println("Inorder");
                System.out.println(tree.inOrder(tree.getRoot()));

                break;

            default:
                break;
        }
    }

    /**
     * This method allows toId enter options toId execute.
     */
    public void init() {
        int functionalityOption;
        boolean exit = false;
        this.kb = new Scanner(System.in);

        showFunctionalitiesMenu();

        try {
            functionalityOption = Integer.parseInt(kb.nextLine());
            this.handleOption(functionalityOption);
        } catch (NumberFormatException e) {
            System.out.println("Wrong option.\n");
        }

        kb.close();
    }
}