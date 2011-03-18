class Post < ActiveRecord::Base
  attr_accessible :content
  
  # associations
  belongs_to :user
end

# == Schema Information
#
# Table name: posts
#
#  id         :integer         not null, primary key
#  content    :string(255)
#  user_id    :integer
#  created_at :datetime
#  updated_at :datetime
#

