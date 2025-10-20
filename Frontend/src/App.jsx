import { Routes, Route, BrowserRouter } from "react-router-dom";
import Main from "./components/Main";
import Login from "./components/auth/Login";
import Register from "./components/auth/Register";
import PostManipulation from "./components/posts/PostManipulation";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route
          path="/posts/new"
          element={<PostManipulation operation="create" />}
        />
        <Route
          path="/posts/:id/edit"
          element={<PostManipulation operation="update" />}
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
