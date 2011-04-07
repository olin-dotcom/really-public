class Comment < ActiveRecord::Base
  attr_accessible :name, :content
  
  has_attached_file :photo
end
