import Skill from "./skill"

interface IProps{
    list: string[]
    onRemove: any
}

function Skills({list,onRemove}:IProps) {
    return <ul>
    {list.map((skill,key) => (
      <Skill key={key} index={key} title={skill} onRemove={onRemove} />
    ))}
  </ul>
}
export default Skills