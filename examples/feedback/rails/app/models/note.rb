class Note < ActiveRecord::Base
  validates :description, :presence => true
end
