class Api::PostsController < ApplicationController
  
  before_filter :authenticate_user!, :except => [ :index ]
  
  respond_to :json
  
  def index
    respond_with(@posts = Post.all)
  end

  def show
    respond_with(@post = Post.find(params[:id]))
  end

  #fixme
  def create
    @post = current_user.posts.build(params[:post])
    if @post.save
      redirect_to @post, :notice => "Successfully created post."
    else
      render :action => 'new'
    end
  end

  #fixme
  def update
    @post = Post.find(params[:id])
    if @post.update_attributes(params[:post])
      redirect_to @post, :notice  => "Successfully updated post."
    else
      render :action => 'edit'
    end
  end

  def destroy
    @post = Post.find(params[:id])
    @post.destroy
    redirect_to posts_url, :notice => "Successfully destroyed post."
  end
end
