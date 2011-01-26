# == Schema Information
# Schema version: 20110125063119
#
# Table name: posts
#
#  id         :integer         not null, primary key
#  content    :string(255)
#  user_id    :integer
#  created_at :datetime
#  updated_at :datetime
#

class Post < ActiveRecord::Base
  belongs_to :user
  attr_accessible :content
  
  validates :content, :length => { :maximum => 140 }
  
end
