require File.dirname(__FILE__) + '/../spec_helper'

describe Post do
  it "should be valid" do
    Post.new.should be_valid
  end
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

