require 'spec_helper'

describe "posts/edit.html.erb" do
  before(:each) do
    @post = assign(:post, stub_model(Post,
      :content => "MyString",
      :user_id => 1
    ))
  end

  it "renders the edit post form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form", :action => post_path(@post), :method => "post" do
      assert_select "input#post_content", :name => "post[content]"
      assert_select "input#post_user_id", :name => "post[user_id]"
    end
  end
end
