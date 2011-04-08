class Comment < ActiveRecord::Base
  attr_accessible :name, :caption, :photo
  
  has_attached_file :photo,
    :styles => {
      :thumb => "100x100#",
      :small  => "150x150>",
      :medium => "200x200>",
      :large => "500x500>"
      }
end
