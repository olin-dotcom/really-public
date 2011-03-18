class Api::PostsController < ApplicationController
  
  before_filter :authenticate_user!, :except => [ :index ]
  
  def index
    @posts = Post.all
    render :json => @posts
  end

  def show
    begin
      @post = Post.find(params[:id])
      render :json => @post      
    rescue Exception => e
      head :not_found
    end
  end

  def create
    @post = current_user.posts.build(:content => params[:content])
    if @post.save
      render :json => @post
    else
      head :bad_request
    end
  end

  def update
    begin
      @post = Post.find(params[:id])      
    rescue Exception => e
      head :not_found
    end
    
    if @post
      if @post.user == current_user
        if @post.update_attributes(:content => params[:content])
          render :json => @post
        else
          head :bad_request
        end
      else
        head :unauthorized
      end
    else
      head :not_found
    end
  end

  def destroy
    begin
      @post = Post.find(params[:id])
      if @post.user == current_user
        @post.destroy
        head :ok
      else
        head :unauthorized
      end
    rescue Exception => e
      head :not_found
    end
  end
end
