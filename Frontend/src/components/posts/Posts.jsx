import NoPosts from "./NoPosts";
import Post from "./Post";

function Posts({ pisma, setPisma }) {
  return (
    <div className="flex-1 flex flex-col items-center overflow-y-auto w-[100%]">
      {pisma.length === 0 ? (
        <NoPosts />
      ) : (
        pisma.map((pismo, i) => (
          <Post key={i} pismo={pismo} setPisma={setPisma} />
        ))
      )}
    </div>
  );
}

export default Posts;
