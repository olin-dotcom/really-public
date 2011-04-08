class Comment < ActiveRecord::Base
  attr_accessible :name, :caption, :photo
  
  has_attached_file :photo, :storage => :s3,
    :styles => {
      :thumb => "100x100#",
      :small  => "150x150>",
      :medium => "200x200>",
      :large => "500x500>"
      },
    :s3_credentials => "#{RAILS_ROOT}/config/amazon_s3.yml",
    :bucket => "chipsahoy-dev",
    :path => ":class/:id/:basename_:style.:extension"
end
