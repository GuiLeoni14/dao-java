package model.dao;

import java.util.ArrayList;
import java.util.List;
import model.ModelException;
import model.entities.Post;
import java.sql.Date;

class MySQLPostDAO implements PostDAO {

    @Override
    public boolean save(Post post) throws ModelException {
        String sqlInsert = "INSERT INTO posts VALUES(DEFAULT, ?, ?);";
        
        try {
            DataBaseHandler dbHandler = new DataBaseHandler();
            dbHandler.prepareStatement(sqlInsert);
            dbHandler.setString(1, post.getContent());
            dbHandler.setDate(2, new java.sql.Date(post.getDate().getTime()));
            
            int rowsAffected = dbHandler.executeUpdate();
            dbHandler.close();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new ModelException("Error saving post: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean update(Post post) throws ModelException {
        String sqlUpdate = "UPDATE posts SET content = ?, date = ? WHERE id = ?;";
        
        try {
            DataBaseHandler dbHandler = new DataBaseHandler();
            dbHandler.prepareStatement(sqlUpdate);
            dbHandler.setString(1, post.getContent());
            dbHandler.setDate(2, new java.sql.Date(post.getDate().getTime()));
            dbHandler.setInt(3, post.getId());
            
            int rowsAffected = dbHandler.executeUpdate();
            dbHandler.close();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new ModelException("Error updating post: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(Post post) throws ModelException {
        String sqlDelete = "DELETE FROM posts WHERE id = ?;";
        
        try {
            DataBaseHandler dbHandler = new DataBaseHandler();
            dbHandler.prepareStatement(sqlDelete);
            dbHandler.setInt(1, post.getId());
            
            int rowsAffected = dbHandler.executeUpdate();
            dbHandler.close();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new ModelException("Error deleting post: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Post> listAll() throws ModelException {
        List<Post> posts = new ArrayList<>();
        String sqlQuery = "SELECT * FROM posts;";
        
        try {
            DataBaseHandler dbHandler = new DataBaseHandler();
            dbHandler.statement();
            ResultSet resultSet = dbHandler.executeQuery();
            
            while (resultSet.next()) {
                int postId = resultSet.getInt("id");
                String content = resultSet.getString("content");
                Date date = resultSet.getDate("date");
                
                Post post = new Post(postId);
                post.setContent(content);
                post.setDate(date);
                
                posts.add(post);
            }
            
            dbHandler.close();
            return posts;
        } catch (SQLException e) {
            throw new ModelException("Error listing posts: " + e.getMessage(), e);
        }
    }

    @Override
    public Post findById(int id) throws ModelException {
        String sql = "SELECT * FROM posts WHERE id = ?;";
        
        try {
            DataBaseHandler dbHandler = new DataBaseHandler();
            dbHandler.prepareStatement(sql);
            dbHandler.setInt(1, id);
            
            ResultSet resultSet = dbHandler.executeQuery();
            Post post = null;
            if (resultSet.next()) {
                int postId = resultSet.getInt("id");
                String content = resultSet.getString("content");
                Date date = resultSet.getDate("date");
                
                post = new Post(postId);
                post.setContent(content);
                post.setDate(date);
            }
            
            dbHandler.close();
            return post;
        } catch (SQLException e) {
            throw new ModelException("Error finding post by ID: " + e.getMessage(), e);
        }
    }
}
